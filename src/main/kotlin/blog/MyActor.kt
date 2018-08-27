package blog

import akka.actor.AbstractActor
import akka.japi.pf.ReceiveBuilder
import java.lang.Thread.sleep

class MyActor : AbstractActor() {

    var count: Int = 0

    override fun createReceive() = ReceiveBuilder().match(String::class.java) {
        println(self.path().name() + " DOING IT")
        sleep(5000)
        sender.tell(it + self.path().name(), self)
        println(self.path().name() + " FINISH IT " + count++)
    }.build()
}