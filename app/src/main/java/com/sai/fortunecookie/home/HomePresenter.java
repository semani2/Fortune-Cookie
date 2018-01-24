package com.sai.fortunecookie.home;

import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.logger.ILogger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sai on 1/23/18.
 */

public class HomePresenter implements HomeMVP.Presenter<HomeMVP.View> {

    private static final String TAG = HomePresenter.class.getSimpleName();

    private final HomeMVP.Model mModel;

    private final ILogger mLogger;

    private HomeMVP.View mView;

    private Disposable disposable;

    private CompositeDisposable networkDisposables = new CompositeDisposable();

    @Inject
    public HomePresenter(HomeMVP.Model mModel, ILogger logger) {
        this.mModel = mModel;
        this.mLogger = logger;
    }

    @Override
    public void setView(HomeMVP.View view) {
        this.mView = view;
    }

    @Override
    public void loadFortuneMessage() {
        mView.showLoading();

        final DisposableObserver<FortuneMessage> observer = new DisposableObserver<FortuneMessage>() {
            @Override
            public void onNext(FortuneMessage fortuneMessage) {
                mLogger.d(TAG, "Fetching message successful!");

                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }

                StringBuilder sb = new StringBuilder();
                for (String text : fortuneMessage.getFortune()) {
                    sb.append(text);
                }

                mView.displayFortuneMessage(sb.toString());
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.showDefaultmessage();
                mView.hideLoading();
            }

            @Override
            public void onComplete() {
                mLogger.d(TAG, "On Complete called");
            }
        };

        disposable = mModel.loadMessage()
                .retry()
                .subscribeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
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

    @Override
    public HomeMVP.View getView() {
        return mView;
    }
}
