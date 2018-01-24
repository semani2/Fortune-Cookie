package com.sai.fortunecookie.app;

import android.app.Application;

import com.sai.fortunecookie.api.FortuneApiModule;
import com.sai.fortunecookie.home.HomeModule;
import com.sai.fortunecookie.logger.LoggerModule;

import timber.log.Timber;

/**
 * Created by sai on 1/23/18.
 */

public class App extends Application{

    private ApplicationComponent mApplicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .homeModule(new HomeModule())
                .fortuneApiModule(new FortuneApiModule())
                .loggerModule(new LoggerModule())
                .build();

        Timber.plant(new Timber.DebugTree());
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
