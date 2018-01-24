package com.sai.fortunecookie.repository;

import com.sai.fortunecookie.api.FortuneApiService;
import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Observable;
import io.reactivex.Single;
import timber.log.Timber;


/**
 * Created by sai on 1/23/18.
 */

public class Repository implements IRepository {

    private final FortuneApiService mFortuneApiService;

    public Repository(FortuneApiService mFortuneApiService) {
        this.mFortuneApiService = mFortuneApiService;
    }

    @Override
    public Observable<FortuneMessage> getMessage() {
        Timber.d("Calling get message");
        return mFortuneApiService.getFortuneMessage();
    }
}

