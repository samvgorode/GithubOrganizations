package com.example.who.githuborganizations.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.example.who.githuborganizations.R;

import butterknife.ButterKnife;

/**
 * Created by who on 28.09.2017.
 */

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(OrganizationsActivity.getNewIntent(SplashActivity.this));
            }
        }, 1500);
    }
}
