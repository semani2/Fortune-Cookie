package com.sai.fortunecookie.logger;

/**
 * Created by sai on 1/24/18.
 */

public interface ILogger {

    /**
     * Method to log debug messages
     * @param message
     */
    void d(String tag, String message);

    /**
     * Method to log debug messages
     * @param throwable
     */
    void e(String tag, Throwable throwable);
}
