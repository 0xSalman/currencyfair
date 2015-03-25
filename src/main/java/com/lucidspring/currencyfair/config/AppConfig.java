package com.lucidspring.currencyfair.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Application configuration that reads properties from application.properties file
 */

@Component
@ConfigurationProperties(prefix="app")
public class AppConfig {

	private String cloudDBService;
	private String mqDestination;
	private String akkaDestination;
	private String tradeConsumer;
	private String tradeProcessor;
    private int akkaRoutees;
    private String akkaActorSystem;

    public String getAkkaActorSystem() {
        return akkaActorSystem;
    }

    public void setAkkaActorSystem(String akkaActorSystem) {
        this.akkaActorSystem = akkaActorSystem;
    }

    public int getAkkaRoutees() {
        return akkaRoutees;
    }

    public void setAkkaRoutees(int akkaRoutees) {
        this.akkaRoutees = akkaRoutees;
    }

    public String getCloudDBService() {
		return cloudDBService;
	}

	public void setCloudDBService(String cloudDBService) {
		this.cloudDBService = cloudDBService;
	}

	public String getMqDestination() {
		return mqDestination;
	}

	public void setMqDestination(String mqDestination) {
		this.mqDestination = mqDestination;
	}

	public String getAkkaDestination() {
		return akkaDestination;
	}

	public void setAkkaDestination(String akkaDestination) {
		this.akkaDestination = akkaDestination;
	}

	public String getTradeConsumer() {
		return tradeConsumer;
	}

	public void setTradeConsumer(String tradeConsumer) {
		this.tradeConsumer = tradeConsumer;
	}

	public String getTradeProcessor() {
		return tradeProcessor;
	}

	public void setTradeProcessor(String tradeProcessor) {
		this.tradeProcessor = tradeProcessor;
	}
}
