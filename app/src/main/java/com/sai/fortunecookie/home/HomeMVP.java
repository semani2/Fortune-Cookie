package com.sai.fortunecookie.home;

/**
 * Created by sai on 1/23/18.
 */

public interface HomeMVP {

    interface Model {
        void loadMessage();
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
