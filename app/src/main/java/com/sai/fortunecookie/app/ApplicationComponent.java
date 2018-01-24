package com.sai.fortunecookie.app;

import com.sai.fortunecookie.api.FortuneApiModule;
import com.sai.fortunecookie.home.HomeActivity;
import com.sai.fortunecookie.home.HomeModule;
import com.sai.fortunecookie.logger.LoggerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sai on 1/23/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class, HomeModule.class, FortuneApiModule.class, LoggerModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity target);
}
