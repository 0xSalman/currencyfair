package com.lucidspring.currencyfair.controller;

import com.lucidspring.currencyfair.config.AppConfig;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.ConsumerService;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restful controller to consume trade messages in JSON format
 */

@RestController
public class ConsumerController {

	@Autowired
	private AppConfig appConfig;
	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/sendTrade", method = RequestMethod.POST, consumes = "application/json")
	public void receiveTrade(@RequestBody TradeEntity trade) {

        LoggerUtil.logEnter(ConsumerController.class, LogLevel.INFO, "receiveTrade", trade);

        try {
            ConsumerService consumerService = applicationContext.getBean(appConfig.getTradeConsumer(),
                                                                   ConsumerService.class);
	        trade.setCurrencyPair("");
            consumerService.saveTrade(trade);
            LoggerUtil.logInfo(ConsumerController.class, "Successfully called message consumer service");
        } catch (Exception e) {
            LoggerUtil.logError(ConsumerController.class, "Error occurred: ", e);
        }

        LoggerUtil.logExit(ConsumerController.class, LogLevel.INFO, "receiveTrade");
    }
}
