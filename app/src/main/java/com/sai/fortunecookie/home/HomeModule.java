package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.FortuneApiService;
import com.sai.fortunecookie.logger.ILogger;
import com.sai.fortunecookie.repository.IRepository;
import com.sai.fortunecookie.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sai on 1/23/18.
 */

@Module
public class HomeModule {

    @Provides
    public HomeMVP.Presenter<HomeMVP.View> provideHomePresenter(HomeMVP.Model model, ILogger logger) {
        return new HomePresenter(model, logger);
    }

    @Provides
    public HomeMVP.Model provideHomeModel(IRepository repository) {
        return new HomeModel(repository);
    }

    @Singleton
    @Provides
    public IRepository provideRepository(FortuneApiService fortuneApiService) {
        return new Repository(fortuneApiService);
    }
}
