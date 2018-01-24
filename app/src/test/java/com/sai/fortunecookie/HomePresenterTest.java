package com.sai.fortunecookie;

import android.support.annotation.NonNull;

import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.home.HomeMVP;
import com.sai.fortunecookie.home.HomePresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sai on 1/24/18.
 */

public class HomePresenterTest {

    @Mock
    private HomeMVP.Model mModel;

    @Mock
    private HomeMVP.View mView;

    private  HomeMVP.Presenter<HomeMVP.View> mPresenter;

    private final FortuneMessage validResponse = new FortuneMessage();

    /**
     * Setting up RxSchedulers has been taken from
     * https://stackoverflow.com/questions/43356314/android-rxjava-2-junit-test-getmainlooper-in-android-os-looper-not-mocked-runt
     */
    private static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Scheduler.Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new HomePresenter(mModel);
        mPresenter.setView(mView);

        validResponse.setFortune(new String[] {"This is a fortune message"});
    }

    @Test
    public void testViewInPresenterNotNull() {
        assertNotNull(mPresenter.getView());
    }

    @Test
    public void testValidFortuneMessage() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        when(mModel.loadMessage()).thenReturn(Observable.just(validResponse));

        mPresenter.loadFortuneMessage();

        verify(mModel, times(1)).loadMessage();

        InOrder inorder = Mockito.inOrder(mView);

        inorder.verify(mView, times(1)).showLoading();
        inorder.verify(mView, times(1))
                .displayFortuneMessage(validResponse.getFortune()[0]);
        inorder.verify(mView, times(1)).hideLoading();
    }

    @Test
    public void testTimeoutScenarioWithDefaultMessage() {
        setUpRxSchedulers();
        when(mModel.loadMessage()).thenReturn(Observable.error(new Exception("hola")));

        mPresenter.loadFortuneMessage();

        InOrder inorder = Mockito.inOrder(mView);

        inorder.verify(mView, times(1)).showLoading();
        inorder.verify(mView, times(1))
                .showDefaultmessage();
        inorder.verify(mView, times(1)).hideLoading();
    }
}
