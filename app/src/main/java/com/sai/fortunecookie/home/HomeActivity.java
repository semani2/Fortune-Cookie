package com.sai.fortunecookie.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    @BindView(R.id.loading_progress_bar)
    ProgressBar loadingProgressBar;

    @BindView(R.id.fortune_cookie_image_view)
    ImageView fortuneCookieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupDependencyInjection();

        refreshFAB.setOnClickListener(view -> mPresenter.loadFortuneMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.loadFortuneMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.rxUnsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxDestroy();
    }

    private void setupDependencyInjection() {
        ((App)getApplication()).getComponent().inject(this);

        ButterKnife.bind(this);
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        refreshFAB.setVisibility(View.GONE);
        fortuneMessageTextView.setVisibility(View.INVISIBLE);
        //animateImage(true);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
        refreshFAB.setVisibility(View.VISIBLE);
        fortuneMessageTextView.setVisibility(View.VISIBLE);
        //animateImage(false);
    }

    @Override
    public void displayFortuneMessage(String message) {
        fortuneMessageTextView.setText(message);
    }


    @Override
    public void showDefaultmessage() {
        fortuneMessageTextView.setText(getString(R.string.str_default_fortune_message));
    }

    //In progress...
    /*private void animateImage(final boolean isTopToBottom) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation animation = new TranslateAnimation(0, 0, !isTopToBottom ? 200 : 0, isTopToBottom ? 200 : 0);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if(isTopToBottom) fortuneMessageTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                       if(!isTopToBottom) fortuneMessageTextView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fortuneCookieImageView.startAnimation(animation);
            }
        });
    }*/
}
