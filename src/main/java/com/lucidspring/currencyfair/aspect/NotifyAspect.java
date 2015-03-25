package com.lucidspring.currencyfair.aspect;

import com.lucidspring.currencyfair.config.SpringApplicationContextHolder;
import com.lucidspring.currencyfair.util.LoggerUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * AOP class to intercept @NotifyClient annotation in service package
 * and update websocket subscribed clients
 *
 * Note: In order to use Spring IOC for AspectJ class, aspectOf methods need to be configured
 *      This was achieved via annotations and creating a SpringContext holder class
 */

@DependsOn("springApplicationContextHolder")
@Configuration
@Aspect
public class NotifyAspect {

	@Autowired
	private SimpMessagingTemplate template;

	public static NotifyAspect aspectOf() {
		return SpringApplicationContextHolder.getApplicationContext().getBean(NotifyAspect.class);
	}

	@Pointcut(value = "@annotation(notifyClients)")
	public void notifyPointcut(NotifyClients notifyClients) {}

	@Pointcut("execution(* com.lucidspring.currencyfair.service..*.*(..))")
	public void methodPointcut() {}

	@AfterReturning(value = "methodPointcut() && notifyPointcut(notifyClients)", returning = "result")
	public void notifyClients(NotifyClients notifyClients, Object result) throws Throwable {
		// send the whole list to client side
		LoggerUtil.logDebug(NotifyAspect.class, "Notify websocket client subscribed to {}", notifyClients.value());
		template.convertAndSend(notifyClients.value(), result);
	}

}