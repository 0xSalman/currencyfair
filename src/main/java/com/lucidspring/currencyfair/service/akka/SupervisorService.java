package com.lucidspring.currencyfair.service.akka;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.SmallestMailboxRoutingLogic;
import com.lucidspring.currencyfair.config.AppConfig;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import com.lucidspring.currencyfair.util.akka.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Akka supervisor actor that receives a trade and sends to defined actors
 * Object specific actors are initialized when the bean is created
 */

@Service("supervisorActor")
public class SupervisorService extends UntypedActor {

    @Autowired
    private SpringExtension springExtension;
    @Autowired
    private AppConfig appConfig;

    private Router router;

    @Override
    public void preStart() {

        LoggerUtil.logDebug(SupervisorService.class, "Initialize Akka router in supervisor actor");

        try {
            // create number of defined receiver routees and initialize the router
            List<Routee> routeList = new ArrayList<>();
            for (int i = 0; i < appConfig.getAkkaRoutees(); i++) {
                routeList.add(new ActorRefRoutee(getActor()));
            }
            router = new Router(new SmallestMailboxRoutingLogic(), routeList);

            super.preStart();
        } catch (Exception e) {
            LoggerUtil.logError(SupervisorService.class, "Error occurred: ", e);
        }
    }

    @Override
    public void onReceive(Object message) {

        LoggerUtil.logEnter(SupervisorService.class, LogLevel.INFO, "onReceive", message);

        try {
            if (message instanceof TradeEntity) {
                router.route(message, getSender());
                LoggerUtil.logInfo(SupervisorService.class, "Successfully delegated the message to receiverActor");
            } else if (message instanceof Terminated) {
                // Re-add receiver actors if one failed
                router = router.removeRoutee(((Terminated) message).actor());
                router = router.addRoutee(new ActorRefRoutee(getActor()));
                LoggerUtil.logDebug(SupervisorService.class, "Re-added terminated receiver routee");
            } else {
                LoggerUtil.logError(SupervisorService.class, "Unable to handle message {}", message);
            }
        } catch (Exception e) {
            LoggerUtil.logError(SupervisorService.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(SupervisorService.class, LogLevel.INFO, "onReceive");
    }

    private ActorRef getActor() {
        ActorRef actor = getContext().actorOf(springExtension.props("receiverActor"));
        getContext().watch(actor);
        return actor;
    }
}
