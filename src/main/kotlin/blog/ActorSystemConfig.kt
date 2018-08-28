package blog

import akka.actor.ActorSystem
import com.typesafe.config.Config
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import com.typesafe.config.ConfigFactory




@Configuration
//@ComponentScan(basePackages = arrayOf("blog") )
class ActorSystemConfig (val applicationContext: ApplicationContext, val springExtension: SpringExtension){

    @Bean
    fun actorSystem(): ActorSystem {
        val system = ActorSystem.create("AkkaJavaSpring", akkaConfiguration())
        springExtension.initialize(applicationContext);
        return system
    }

    @Bean
    fun akkaConfiguration(): Config {
        return ConfigFactory.load()
    }

}