package com.sai.fortunecookie.app;

import com.sai.fortunecookie.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sai on 1/23/18.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(HomeActivity target);
}
