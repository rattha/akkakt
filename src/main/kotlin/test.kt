import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@SpringBootApplication
@EnableAutoConfiguration(exclude = arrayOf(DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class, HibernateJpaAutoConfiguration::class))
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}



//fun main(args: Array<String>) {
//
//    class HelloKotlinActor : AbstractLoggingActor() {
//        override fun createReceive() = ReceiveBuilder().match(String::class.java) { println(it) }.build()
//    }
//
//    val actorSystem = ActorSystem.create("part1")
//    val actorRef = actorSystem.actorOf(Props.create(HelloKotlinActor::class.java))
//    actorSystem.log().info("Sending Hello Kotlin")
//    actorRef.tell("Hello Kotlin", ActorRef.noSender())
//
//}