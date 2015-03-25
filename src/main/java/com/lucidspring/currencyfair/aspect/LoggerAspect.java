package com.lucidspring.currencyfair.aspect;

import com.lucidspring.currencyfair.util.LoggerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggerAspect {

	@Pointcut(value = "@annotation(loggable)")
	public void annotationPointcut(Loggable loggable) {}

	@Pointcut("execution(* com.lucidspring.currencyfair..*.*(..))")
//	@Pointcut("execution(* com.lucidspring.currencyfair.controller.IndexController.*(..))")
	public void methodPointcut() {}

    @Before(value="annotationPointcut(loggable) && methodPointcut()", argNames="joinPoint, loggable")
    public void logEnter(JoinPoint joinPoint, Loggable loggable) {

        Class clazz = joinPoint.getTarget().getClass();
        String method = joinPoint.getSignature().getName();

        LoggerUtil.logEnter(clazz, loggable.value(), method, joinPoint.getArgs());
    }

    @After(value="annotationPointcut(loggable) && methodPointcut()", argNames="joinPoint, loggable")
    public void logExit(JoinPoint joinPoint, Loggable loggable) {

        Class clazz = joinPoint.getTarget().getClass();
        String method = joinPoint.getSignature().getName();

        LoggerUtil.logExit(clazz, loggable.value(), method);
    }

}
