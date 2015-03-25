package com.lucidspring.currencyfair.service.akka;

import akka.actor.UntypedActor;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.TradeRepository;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Akka receiver actor that receives a trade from supervisor actor and persists to DB
 */

@Service("receiverActor")
@Scope("prototype")
public class ReceiverService extends UntypedActor {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public void onReceive(Object message) {

        LoggerUtil.logEnter(ReceiverService.class, LogLevel.INFO, "onReceive", message);

        try {
            tradeRepository.save((TradeEntity) message);
            LoggerUtil.logInfo(ReceiverService.class, "Successfully saved message");
        } catch (Exception e) {
            LoggerUtil.logError(ReceiverService.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(ReceiverService.class, LogLevel.INFO, "onReceive");
    }
}
