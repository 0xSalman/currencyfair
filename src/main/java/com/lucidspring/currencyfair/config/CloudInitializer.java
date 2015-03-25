package com.lucidspring.currencyfair.config;

import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Configure cloudfoundry profile
 */

public class CloudInitializer implements ApplicationContextInitializer<AnnotationConfigEmbeddedWebApplicationContext> {

    @Override
    public void initialize(AnnotationConfigEmbeddedWebApplicationContext applicationContext) {

        Cloud cloud = getCloud();
        ConfigurableEnvironment appEnvironment = applicationContext.getEnvironment();

        if (cloud != null) {
            appEnvironment.addActiveProfile("cloud");
        }
    }

    private Cloud getCloud() {
        try {
            CloudFactory cloudFactory = new CloudFactory();
            return cloudFactory.getCloud();
        } catch (CloudException ce) {
            return null;
        }
    }
}