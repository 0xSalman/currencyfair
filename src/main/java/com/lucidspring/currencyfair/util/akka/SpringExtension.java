package com.lucidspring.currencyfair.util.akka;

import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * An extension class to integrate spring context with Akka actor system
 * this class would be used by SpringActorProducer
 */

@Component
public class SpringExtension implements Extension {

    @Autowired
    private ApplicationContext applicationContext;

    public Props props(String actorBeanName) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
    }

}
