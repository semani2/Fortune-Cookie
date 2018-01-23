package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.repository.IRepository;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by sai on 1/23/18.
 */

public class HomeModel implements HomeMVP.Model {

    private final IRepository mRepository;

    @Inject
    public HomeModel(IRepository mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public Single<FortuneMessage> loadMessage() {
        return mRepository.getMessage();
    }
}
