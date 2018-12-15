package blog.actor

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.japi.pf.ReceiveBuilder
import akka.stream.ActorMaterializer
import akka.stream.OverflowStrategy
import akka.stream.javadsl.Sink
import akka.stream.javadsl.Source
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("prototype")
class MyActorToStream(system: ActorSystem) : AbstractActor() {

    var actStream: Source<String, ActorRef>? = null
    var a: ActorRef? = null

    init {
        val mat = ActorMaterializer.create(system)

        actStream = Source.actorRef<String>(100, OverflowStrategy.fail())

        a = actStream?.to(Sink.foreach { println(it) })?.run(mat)
    }

    override fun createReceive() = ReceiveBuilder().match(String::class.java) {

        a?.tell(it, self)

    }.build()

}