package com.lucidspring.currencyfair.service.mq;

import com.lucidspring.currencyfair.config.AppConfig;
import com.lucidspring.currencyfair.entity.TradeEntity;
import com.lucidspring.currencyfair.service.ConsumerService;
import com.lucidspring.currencyfair.util.LogLevel;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Trade consumer service that relieves a trade from the rest consumer controller and sends to activeMQ destination
 */

@Service("mq")
public class MqConsumerService implements ConsumerService {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private AppConfig appConfig;

	@Override
	public void saveTrade(final TradeEntity trade) {

        LoggerUtil.logEnter(MqConsumerService.class, LogLevel.INFO, "saveTrade", trade);

        try {
            MessageCreator messageCreator = new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(trade);
                }
            };

            jmsTemplate.send(appConfig.getMqDestination(), messageCreator);

            LoggerUtil.logInfo(MqConsumerService.class, "Successfully sent message to {}", appConfig.getMqDestination());
        } catch (Exception e) {
            LoggerUtil.logError(MqConsumerService.class, "Error occurred: ", e);
        }

        LoggerUtil.logEnter(MqConsumerService.class, LogLevel.INFO, "saveTrade");
    }
}
