package blog

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ActorSystemConfig {

    @Bean
    fun actorSystem(): ActorSystem {
        return ActorSystem.create("part1", ConfigFactory.parseResources("application.conf"))
    }

}