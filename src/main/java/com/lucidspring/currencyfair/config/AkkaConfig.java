package com.lucidspring.currencyfair.config;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure required beans to initialize Akka
 */

@Configuration
public class AkkaConfig {

    @Autowired
    private AppConfig appConfig;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create(appConfig.getAkkaActorSystem(), akkaConfiguration());
        return system;
    }

    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }

}
