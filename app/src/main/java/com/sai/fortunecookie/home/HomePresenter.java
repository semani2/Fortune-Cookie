package com.sai.fortunecookie.home;

import android.os.CountDownTimer;

import com.sai.fortunecookie.api.model.FortuneMessage;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by sai on 1/23/18.
 */

public class HomePresenter implements HomeMVP.Presenter<HomeMVP.View> {

    private final HomeMVP.Model mModel;

    private HomeMVP.View mView;

    private Disposable disposable;

    private CompositeDisposable networkDisposables = new CompositeDisposable();

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
        mView.showLoading();
        final CountDownTimer timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                // Do nothing
            }

            @Override
            public void onFinish() {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                mView.hideLoading();
                mView.showDefaultmessage();
            }
        }.start();

        final DisposableObserver<FortuneMessage> observer = new DisposableObserver<FortuneMessage>() {
            @Override
            public void onNext(FortuneMessage fortuneMessage) {
                Timber.d("Fetching message successful!");

                timer.cancel();

                if(disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }

                StringBuilder sb = new StringBuilder();
                for(String text: fortuneMessage.getFortune()) {
                    sb.append(text);
                }

                mView.hideLoading();
                mView.displayFortuneMessage(sb.toString());
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {
                Timber.d("On Complete called");
            }
        };

        disposable = mModel.loadMessage()
                .retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        networkDisposables.add(disposable);
    }

    @Override
    public void rxUnsubscribe() {
        if(!networkDisposables.isDisposed()) networkDisposables.clear();
    }

    @Override
    public void rxDestroy() {
        if(!networkDisposables.isDisposed()) networkDisposables.dispose();
    }
}
