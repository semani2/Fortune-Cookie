package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.model.FortuneMessage;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by sai on 1/23/18.
 */

public class HomePresenter implements HomeMVP.Presenter<HomeMVP.View> {

    private final HomeMVP.Model mModel;

    private HomeMVP.View mView;

    @Inject
    public HomePresenter(HomeMVP.Model mModel) {
        this.mModel = mModel;
    }

    @Override
    public void setView(HomeMVP.View view) {
        this.mView = view;
    }

    @Override
    public void loadFortuneMessage() {
        SingleObserver<FortuneMessage> messageSingleObserver = new SingleObserver<FortuneMessage>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("Subscribed to fetch fortune message.");
            }

            @Override
            public void onSuccess(FortuneMessage fortuneMessage) {
                Timber.d("Fetching message successful!");
                if(fortuneMessage == null || fortuneMessage.getFortune().length == 0) {
                    // TODO :: Handle error case
                    return;
                }
                mView.displayFortuneMessage(fortuneMessage.getFortune()[0]);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                mView.showErrorMessage(e.getMessage());
            }
        };

        mModel.loadMessage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(messageSingleObserver);
    }
}
