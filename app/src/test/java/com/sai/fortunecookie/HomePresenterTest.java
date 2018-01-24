package com.sai.fortunecookie;

import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.home.HomeMVP;
import com.sai.fortunecookie.home.HomePresenter;
import com.sai.fortunecookie.logger.ILogger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
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

    @Mock
    private ILogger mLogger;

    private  HomeMVP.Presenter<HomeMVP.View> mPresenter;

    private final FortuneMessage validResponse = new FortuneMessage();

  /*  *//**
     * Setting up RxSchedulers has been taken from
     * https://stackoverflow.com/questions/43356314/android-rxjava-2-junit-test-getmainlooper-in-android-os-looper-not-mocked-runt
     *//*
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
    }*/

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new HomePresenter(mModel, mLogger);
        mPresenter.setView(mView);

        validResponse.setFortune(new String[] {"This is a fortune message"});

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void testViewInPresenterNotNull() {
        assertNotNull(mPresenter.getView());
    }

    @Test
    public void testValidFortuneMessage() {
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
    public void testValidFortuneMessageNotShowDefault() {
        when(mModel.loadMessage()).thenReturn(Observable.just(validResponse));

        mPresenter.loadFortuneMessage();

        verify(mModel, times(1)).loadMessage();
        verify(mView, times(0)).showDefaultmessage();
    }

    /*@Test
    public void testTimeoutScenarioWithDefaultMessage() {
        setUpRxSchedulers();
        //RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        when(mModel.loadMessage()).thenReturn(Observable.error(new Exception("hola")));

        mPresenter.loadFortuneMessage();

        InOrder inorder = Mockito.inOrder(mView);

        inorder.verify(mView, times(1)).showLoading();
        inorder.verify(mView).showDefaultmessage();
        inorder.verify(mView, times(1)).hideLoading();
    }*/
}
