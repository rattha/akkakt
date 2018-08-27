package blog

import akka.actor.AbstractLoggingActor
import akka.actor.ActorRef
import akka.japi.pf.ReceiveBuilder

class MyActor : AbstractLoggingActor() {
    override fun createReceive() = ReceiveBuilder().match(String::class.java) {
        sender.tell("HELLO FROM ACTOR", ActorRef.noSender())
    }.build()
}