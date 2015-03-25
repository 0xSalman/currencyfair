package com.lucidspring.currencyfair.service.mq;

import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.TradeRepository;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MQ trade reliever service which persists the data to database
 */

@Service
public class ReceiverService {

    @Autowired
    private TradeRepository tradeRepository;

	public void receiveMessage(TradeEntity trade) {

        LoggerUtil.logEnter(ReceiverService.class, LogLevel.INFO, "receiveMessage", trade);

        try {
            tradeRepository.save(trade);
            LoggerUtil.logInfo(ReceiverService.class, "Successfully saved message");
        } catch (Exception e) {
            LoggerUtil.logError(ReceiverService.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(ReceiverService.class, LogLevel.INFO, "receiveMessage");
    }

}
