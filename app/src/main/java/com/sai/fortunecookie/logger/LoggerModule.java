package com.sai.fortunecookie.logger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 1/24/18.
 */

@Module
public class LoggerModule {

    @Provides
    @Singleton
    public ILogger providesLogger() {
        return new Logger();
    }
}
