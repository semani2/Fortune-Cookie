package com.sai.fortunecookie.repository;

import com.sai.fortunecookie.api.FortuneApiService;
import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Observable;


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
        return mFortuneApiService.getFortuneMessage();
    }
}

