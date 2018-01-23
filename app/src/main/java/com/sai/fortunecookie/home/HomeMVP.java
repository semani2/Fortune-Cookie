package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.model.FortuneMessage;

import io.reactivex.Single;

/**
 * Created by sai on 1/23/18.
 */

public interface HomeMVP {

    interface Model {
        Single<FortuneMessage> loadMessage();
    }

    interface View {
        void showLoading();

        void hideLoading();

        void displayFortuneMessage(String message);

        void showErrorMessage(String errorMessage);
    }

    interface Presenter<T> {
        void setView(T view);

        void loadFortuneMessage();
    }
}
