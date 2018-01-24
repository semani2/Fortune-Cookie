package com.sai.fortunecookie.repository;

import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Observable;

/**
 * Created by sai on 1/23/18.
 */

public interface IRepository {

    /**
     * Method to return the fortune message from the API
     * @return Single<FortuneMessage>
     */
    Observable<FortuneMessage> getMessage();
}
