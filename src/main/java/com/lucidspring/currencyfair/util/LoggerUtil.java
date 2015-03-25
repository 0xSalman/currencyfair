package com.lucidspring.currencyfair.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A logger utility class
 */

public class LoggerUtil {


    public static <T> void logServiceError(Class<T> clazz, String methodName, Throwable ex, Object...objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error("Exception in Service Layer: {}({}) - Original Exception was {}", methodName, objects, ex);
    }

    public static <T> void logError(Class<T> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message);
    }

    public static <T> void logError(Class<T> clazz, String message, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, params);
    }

    public static <T> void logInfo(Class<T> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message);
    }

    public static <T> void logInfo(Class<T> clazz, String message, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message, params);
    }

    public static <T> void logDebug(Class<T> clazz, String message) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message);
    }

    public static <T> void logDebug(Class<T> clazz, String message, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message, params);
    }

    public static <T> void logError(Class<T> clazz, String message, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, ex);
    }

    public static <T> void logInfo(Class<T> clazz, String message, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message, ex);
    }

    public static <T> void logDebug(Class<T> clazz, String message, Throwable ex) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message, ex);
    }

    /**
     * Method to be used for logging start of Service Methods without input parameters
     *
     * @param methodName
     */
    public static <T> long logEnter(Class<T> clazz, String methodName) {
        Logger logger = LoggerFactory.getLogger(clazz);

        logger.trace("ENTER: {}",  methodName);
        return System.currentTimeMillis();
    }

    public static <T> long logEnter(Class<T> clazz, String methodName, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.trace("ENTER: {}({})", methodName, params);
        return System.currentTimeMillis();
    }

    /**
     * Method to be used for logging start of Service Methods without input parameters
     *
     * @param logType
     * @param methodName
     */
    public static <T> long logEnter(Class<T> clazz, LogLevel logType, String methodName) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logType.equals(LogLevel.INFO)) {
            logger.info("ENTER - {}()", methodName);
        } else {
            logger.debug("ENTER - {}()", methodName);
        }
        return System.currentTimeMillis();
    }


    public static <T> long logEnter(Class<T> clazz, LogLevel logType, String methodName, Object... params) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logType.equals(LogLevel.INFO)) {
            logger.info("ENTER - {}({})", methodName, params);
        } else {
            logger.debug("ENTER - {}({})", methodName, params);
        }
        return System.currentTimeMillis();
    }

    public static <T> void logExit(Class<T> clazz, String methodName, long startTime) {
        Logger logger = LoggerFactory.getLogger(clazz);
        long runTime = System.currentTimeMillis() - startTime;
        logger.trace("EXIT: {} in {}ms", methodName, runTime);
    }

    public static <T> void logExit(Class<T> clazz, String methodName, Object returnValue, long startTime) {
        Logger logger = LoggerFactory.getLogger(clazz);
        long runTime = System.currentTimeMillis() - startTime;
        logger.trace("EXIT: {} in {}ms > {}", methodName, runTime, returnValue);
    }

    public static <T> void logExit(Class<T> clazz, LogLevel logType, String methodName, long startTime) {
        Logger logger = LoggerFactory.getLogger(clazz);
        long runTime = System.currentTimeMillis() - startTime;
        if (logType.equals(LogLevel.INFO)) {
            logger.info("EXIT - {}() in {}ms", methodName, runTime);
        } else {
            logger.debug("EXIT - {}() in {}ms", methodName, runTime);
        }
    }

    public static <T> void logExit(Class<T> clazz, LogLevel logType, String methodName) {
        Logger logger = LoggerFactory.getLogger(clazz);
        if (logType.equals(LogLevel.INFO)) {
            logger.info("EXIT - {}()", methodName);
        } else {
            logger.debug("EXIT - {}()", methodName);
        }
    }

    public static <T> void logExit(Class<T> clazz, LogLevel logType, String methodName, Object returnValue, long startTime) {
        Logger logger = LoggerFactory.getLogger(clazz);
        long runTime = System.currentTimeMillis() - startTime;
        if (logType.equals(LogLevel.INFO)) {
            logger.info("EXIT - {}() in {}ms > {}", methodName, runTime, returnValue);
        } else {
            logger.debug("EXIT - {}() in {}ms > {}", methodName, runTime, returnValue);
        }
    }
}