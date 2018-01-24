package com.sai.fortunecookie;

import com.sai.fortunecookie.api.FortuneApiService;
import com.sai.fortunecookie.api.model.FortuneMessage;
import com.sai.fortunecookie.repository.IRepository;
import com.sai.fortunecookie.repository.Repository;

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

public class RepositoryTest {

    @Mock
    FortuneApiService mFortuneApiService;

    private IRepository mRepository;

    private final FortuneMessage validResponse = new FortuneMessage();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mFortuneApiService.getFortuneMessage()).thenReturn(Observable.just(validResponse));

        mRepository = new Repository(mFortuneApiService);
    }

    @Test
    public void test_LoadMessage_ValidResponse_Success() {
        mRepository.getMessage();

        verify(mFortuneApiService, times(1)).getFortuneMessage();
    }
}
