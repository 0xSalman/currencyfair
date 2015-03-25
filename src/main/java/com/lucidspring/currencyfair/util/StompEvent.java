package com.lucidspring.currencyfair.util;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * Utility class to monitor Websocket/Stomp events
 * It could be handy later down the road
 */

@Component
public class StompEvent implements ApplicationListener<SessionConnectEvent> {

	private boolean wsConnected;

	public void onApplicationEvent(SessionConnectEvent event) {
        LoggerUtil.logDebug(StompEvent.class, "WS connection called");
		this.wsConnected = true;
	}

	public boolean isWsConnected() {
		return wsConnected;
	}

	public void setWsConnected(boolean wsConnected) {
		this.wsConnected = wsConnected;
	}
}