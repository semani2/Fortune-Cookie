package com.sai.fortunecookie.logger;

import timber.log.Timber;

/**
 * Created by sai on 1/24/18.
 */

public class Logger implements ILogger {
    @Override
    public void d(String tag, String message) {
        Timber.d(tag, message);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        Timber.e(tag, throwable);
    }
}
