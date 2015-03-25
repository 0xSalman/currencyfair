package com.lucidspring.currencyfair.service.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.lucidspring.currencyfair.config.AppConfig;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.ConsumerService;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import com.lucidspring.currencyfair.util.akka.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Trade consumer service which receivers a trade from the rest controller and sends it to akka supervisor actor
 */

@Service("akka")
public class AkkaConsumerService implements ConsumerService {

    private ActorRef supervisor;

    @Autowired
    public AkkaConsumerService(SpringExtension springExtension, ActorSystem actorSystem, AppConfig appConfig) {

        if (appConfig.getTradeConsumer().equals("akka"))
            supervisor = actorSystem.actorOf(springExtension.props("supervisorActor")
                                                            .withMailbox("akka.default-mailbox"));
    }

    @Override
    public void saveTrade(TradeEntity trade) {

        LoggerUtil.logEnter(AkkaConsumerService.class, LogLevel.INFO, "saveTrade", trade);

        try {
            supervisor.tell(trade, null);
            LoggerUtil.logInfo(AkkaConsumerService.class, "Successfully sent message to akka.default-mailbox");
        } catch (Exception e) {
            LoggerUtil.logError(AkkaConsumerService.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(AkkaConsumerService.class, LogLevel.INFO, "saveTrade");
    }
}
