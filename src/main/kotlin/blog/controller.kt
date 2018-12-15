package blog

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.pattern.Patterns
import akka.util.Timeout
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.atomic.AtomicLong


@RestController
class GreetingController(val actorSystem: ActorSystem, val ext: SpringExtension) {

    val counter = AtomicLong()

    val actor = actorSystem.actorOf(ext.props("myActor"))
    val actorToStream = actorSystem.actorOf(ext.props("myActorToStream"))

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
            "HELLO"

    @GetMapping("/greetActor")
    fun greetingActor(): String {


        val timeout = Timeout(Duration.create(10, "seconds"))
        val future = Patterns.ask(actor, "Hello Actor", timeout)
        //actorSystem.actorOf(Props.create(MyActor::class.java)).ask("Hello Actor", ActorRef.noSender())
        return Await.result(future, timeout.duration()) as String
    }

    @GetMapping("/actToStream")
    fun actToStream(): String {
        actorToStream.tell("TEST", ActorRef.noSender())

        return "DONE"
    }

}