package com.sai.fortunecookie.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.fortunecookie.R;
import com.sai.fortunecookie.app.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeMVP.View{

    @Inject
    HomeMVP.Presenter<HomeMVP.View> mPresenter;

    @BindView(R.id.fortune_message_text_view)
    TextView fortuneMessageTextView;

    @BindView(R.id.refresh_fab)
    FloatingActionButton refreshFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupDependencyInjection();

        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.loadFortuneMessage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadFortuneMessage();
    }

    private void setupDependencyInjection() {
        ((App)getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayFortuneMessage(String message) {
        fortuneMessageTextView.setText(message);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
