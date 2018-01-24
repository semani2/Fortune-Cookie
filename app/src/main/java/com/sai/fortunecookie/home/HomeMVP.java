package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by sai on 1/23/18.
 */

public interface HomeMVP {

    interface Model {
        Observable<FortuneMessage> loadMessage();
    }

    interface View {
        void showLoading();

        void hideLoading();

        void displayFortuneMessage(String message);

        void showDefaultmessage();
    }

    interface Presenter<T> {
        void setView(T view);

        void loadFortuneMessage();
    }
}
