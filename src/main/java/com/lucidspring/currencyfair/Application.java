package com.lucidspring.currencyfair;

import com.lucidspring.currencyfair.config.CloudInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class that runs the application in embedded tomcat
 */

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application {

	public static void main(String[] args) {

		new SpringApplicationBuilder(Application.class)
				.initializers(new CloudInitializer())
				.run(args);
	}
}