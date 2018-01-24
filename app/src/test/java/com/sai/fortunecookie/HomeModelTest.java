package com.sai.fortunecookie;

import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.home.HomeMVP;
import com.sai.fortunecookie.home.HomeModel;
import com.sai.fortunecookie.repository.IRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sai on 1/24/18.
 */

public class HomeModelTest {

    @Mock
    IRepository mRepository;

    private HomeMVP.Model mModel;

    private final FortuneMessage validResponse = new FortuneMessage();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mRepository.getMessage()).thenReturn(Observable.just(validResponse));

        mModel = new HomeModel(mRepository);
    }

    @Test
    public void test_LoadMessage_ValidResponse_Success() {
        mModel.loadMessage();

        verify(mRepository, times(1)).getMessage();
    }
}
