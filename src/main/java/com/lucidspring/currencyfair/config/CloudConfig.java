package com.lucidspring.currencyfair.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Configure mongodb factory for Cloudfoundry profile
 */

@Configuration
@Profile("cloud")
public class CloudConfig extends AbstractCloudConfig {

	@Autowired
	AppConfig appConfig;

	@Bean
	public MongoDbFactory mongoDbFactory() {

		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
		MongoServiceInfo serviceInfo = (MongoServiceInfo) cloud.getServiceInfo(appConfig.getCloudDBService());
		String serviceID = serviceInfo.getId();
		return connectionFactory().mongoDbFactory(serviceID);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDbFactory());
	}
}
