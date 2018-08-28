package blog.actor

import akka.actor.AbstractActor
import akka.japi.pf.ReceiveBuilder
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Lazy
@Scope("prototype")
class MyActor2: AbstractActor() {

    override fun createReceive() = ReceiveBuilder().match(String::class.java) {
        sender.tell(it + " last", self)
    }.build()
}

data class Last(val n: String)