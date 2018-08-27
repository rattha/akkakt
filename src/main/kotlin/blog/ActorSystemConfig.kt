package blog

import akka.actor.ActorSystem
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActorSystemConfig {

    @Bean
    fun actorSystem(): ActorSystem {
        return ActorSystem.create("part1")
    }

}