package com.sai.fortunecookie.api;

import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by sai on 1/23/18.
 */

public interface FortuneApiService {

    @GET("fortune")
    Observable<FortuneMessage> getFortuneMessage();
}
