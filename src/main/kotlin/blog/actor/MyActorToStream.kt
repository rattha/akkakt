package blog.actor

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.japi.pf.ReceiveBuilder
import akka.stream.ActorMaterializer
import akka.stream.OverflowStrategy
import akka.stream.alpakka.elasticsearch.ElasticsearchWriteSettings
import akka.stream.alpakka.elasticsearch.WriteMessage
import akka.stream.alpakka.elasticsearch.javadsl.ElasticsearchFlow
import akka.stream.alpakka.elasticsearch.javadsl.ElasticsearchSink
import akka.stream.javadsl.Sink
import akka.stream.javadsl.Source
import blog.data.MyEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class MyActorToStream(system: ActorSystem) : AbstractActor() {

    var actStream: Source<String, ActorRef>? = null
    var a: ActorRef? = null

    val client = RestClient.builder(HttpHost("localhost", 9200)).build()

    var count = 0

    init {
        val mat = ActorMaterializer.create(system)

        actStream = Source.actorRef<String>(100, OverflowStrategy.fail())

        val sink = ElasticsearchSink.create<Any>(
                "sink3",
                "_doc",
                ElasticsearchWriteSettings.create().withBufferSize(5),
                client,
                ObjectMapper())

        a = actStream
                ?.map { _ -> count++ }
                ?.map { m -> WriteMessage.createIndexMessage(m.toString(), MyEvent("TEST")) }
                ?.via(
                        ElasticsearchFlow.create(
                                "sink3",
                                "_doc",
                                ElasticsearchWriteSettings.create().withBufferSize(5),
                                client,
                                ObjectMapper()))
                ?.to(Sink.foreach { println(it) })?.run(mat)

    }

    override fun createReceive() = ReceiveBuilder().match(String::class.java) {

        a?.tell(it, self)

    }.build()

}