package blog

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong
import akka.actor.*
import akka.pattern.Patterns
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration.Duration


@RestController
class GreetingController(val actorSystem: ActorSystem) {

    val counter = AtomicLong()
    val actor = actorSystem.actorOf(Props.create(MyActor::class.java))

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

}