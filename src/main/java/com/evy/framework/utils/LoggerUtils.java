package com.evy.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A utility class for logging messages and exceptions using Log4j2.
 * <p>
 * This class provides static methods for logging informational and error messages.
 * It uses Log4j2 for logging and ensures that logging is consistent across different classes.
 * </p>
 */
public final class LoggerUtils {

    /**
     * The logger instance for this class.
     */
    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private LoggerUtils() {}

    /**
     * Retrieves a logger instance for the specified class.
     *
     * @param cls the class for which to retrieve the logger
     * @return a Logger instance for the specified class
     */
    private static Logger getLogger(Class<?> cls) {
        return LogManager.getLogger(cls);
    }

    /**
     * Logs an informational message for the specified class.
     *
     * @param cls the class for which to log the message
     * @param msg the informational message to log
     */
    public static void info(Class<?> cls, String msg) {
        getLogger(cls).info(msg);
    }

    /**
     * Logs an error message and a throwable for the specified class.
     *
     * @param cls the class for which to log the error
     * @param msg the error message to log
     * @param t   the throwable to log
     */
    public static void error(Class<?> cls, String msg, Throwable t) {
        getLogger(cls).error(msg, t);
    }
}
