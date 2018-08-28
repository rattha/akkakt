package blog

import akka.actor.ActorSystem
import akka.pattern.Patterns
import akka.util.Timeout
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import scala.concurrent.Await
import scala.concurrent.duration.Duration

@RunWith(SpringRunner::class)
@SpringBootTest
class BlogApplicationTests {

    @Autowired
    lateinit var ext: SpringExtension
    @Autowired
    lateinit var actorSystem: ActorSystem

	@Test
	fun testAaa() {
		val timeout = Timeout(Duration.create(10, "seconds"))
        val actor = actorSystem.actorOf(ext.props("myActor"))

		val future = Patterns.ask(actor, "Hello Actor", timeout)
		//actorSystem.actorOf(Props.create(MyActor::class.java)).ask("Hello Actor", ActorRef.noSender())
		val a = Await.result(future, timeout.duration()) as String

        println(a)
	}

}
