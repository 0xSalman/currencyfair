package com.lucidspring.currencyfair.config;


import com.lucidspring.currencyfair.service.mq.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

import javax.jms.ConnectionFactory;

/**
 * Configure MQ listener and container
 */

@Configuration
public class JmsConfig {

	@Autowired
	AppConfig appConfig;

    @Bean
    MessageListenerAdapter adapter(ReceiverService receiverService) {
        MessageListenerAdapter messageListener
                = new MessageListenerAdapter(receiverService);
        messageListener.setDefaultListenerMethod("receiveMessage");
        return messageListener;
    }

	@Bean
	SimpleMessageListenerContainer container(MessageListenerAdapter messageListener,
                                             ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setMessageListener(messageListener);
		container.setConnectionFactory(connectionFactory);
		container.setDestinationName(appConfig.getMqDestination());
		return container;
	}

}
