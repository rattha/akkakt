package blog.actor

import akka.actor.AbstractActor
import akka.actor.ActorSystem
import akka.japi.pf.ReceiveBuilder
import akka.pattern.Patterns
import akka.util.Timeout
import blog.SpringExtension
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.lang.Thread.sleep

@Component
@Lazy
@Scope("prototype")
class MyActor(val actorSystem: ActorSystem, val ext: SpringExtension): AbstractActor() {

    var count: Int = 0

    override fun createReceive() = ReceiveBuilder().match(String::class.java) {

        sleep(5000)

        val actor2 = actorSystem.actorOf(ext.props("myActor2"))

        val timeout = Timeout(Duration.create(10, "seconds"))
        val future = Patterns.ask(actor2, "Hello Actor2 " + count++, timeout)

        sender.tell(Await.result(future, timeout.duration()) as String, self)

    }.build()
}