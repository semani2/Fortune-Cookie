package com.sai.fortunecookie.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sai.fortunecookie.R;

public class HomeActivity extends AppCompatActivity implements HomeMVP.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayFortuneMessage(String message) {

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }
}
