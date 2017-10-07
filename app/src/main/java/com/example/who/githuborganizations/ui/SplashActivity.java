package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.example.who.githuborganizations.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by who on 28.09.2017.
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.bar)
    ProgressBar bar;

    private static final String EXIT = "EXIT";
    private Handler handler = new Handler();
    private boolean isExit = false;

    public static Intent getNewIntent(Context context, boolean isExit) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXIT, isExit);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        fetchIntent();
    }

    private void fetchIntent() {
        bar.setVisibility(View.VISIBLE);
        handler.removeCallbacksAndMessages(null);
        Intent intent = getIntent();
        if (intent.hasExtra(EXIT)) isExit = intent.getBooleanExtra(EXIT, false);
        if (isExit) {
            handler.postDelayed(() -> {
                bar.setVisibility(View.GONE);
                SplashActivity.this.finish();
            }, 500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isExit) init();
    }

    private void init() {
        handler.postDelayed(() -> {
            bar.setVisibility(View.GONE);
            startActivity(OrganizationsActivity.getNewIntent(SplashActivity.this));
        }, 500);
    }
}
