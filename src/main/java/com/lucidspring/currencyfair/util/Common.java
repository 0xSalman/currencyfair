package com.lucidspring.currencyfair.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * A utility class that holds common methods that would be used across different classes in the application
 */

@Component
public class Common {

    @Autowired
    private SimpMessagingTemplate template;

    public void notifyWSClient(String url, Object object) {
        LoggerUtil.logDebug(Common.class, "Notify websocket client subscribed to {}", url);
        template.convertAndSend(url, object);
    }
}
