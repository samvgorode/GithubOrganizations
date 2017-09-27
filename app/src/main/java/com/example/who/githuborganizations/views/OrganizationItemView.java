package com.example.who.githuborganizations.views;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.ui.OrganizationsActivity;
import com.example.who.githuborganizations.ui.RepositoriesActivity;
import com.skydoves.medal.MedalAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by who on 26.09.2017.
 */

public class OrganizationItemView extends RelativeLayout {

    @BindView(R.id.ivImageOfOrg)
    ImageView ivImageOfOrg;
    @BindView(R.id.tvOrgLogin)
    TextView tvOrgLogin;
    @BindView(R.id.tvOrgLocation)
    TextView tvOrgLocation;
    @BindView(R.id.tvOrgUrl)
    TextView tvOrgUrl;

    private Organization item;
    private MedalAnimation medalAnimation;

    public OrganizationItemView(Context context) {
        super(context);
        init();
    }

    public OrganizationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrganizationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.organizations_item_view, this);
        ButterKnife.bind(this);
        initAnimator();
    }

    private void initAnimator(){
        medalAnimation = new MedalAnimation.Builder()
                .setDirection(MedalAnimation.REVERSE)
                .setSpeed(1000)
                .setTurn(1)
                .setLoop(1)
                .build();
    }

    void setImageOfOrg(String src) {
        if (isNetworkConnected()) {
            Glide.with(getContext()).load(src).into(ivImageOfOrg);
        }
    }

    void setTitleOfOrg(String login) {
        tvOrgLogin.setText(login);
    }

    void setAuthorOfOrg(String location) {
        tvOrgLocation.setText(location);
    }

    void setUrlOfOrg(String url) {
        tvOrgUrl.setText(url);
    }

    public void setItem(Organization item) {
        if (item != null) {
            OrganizationItemView.this.item = item;
            final String imageUri = item.getAvatarUrl();
            final String login = item.getLogin();
            final String location = item.getLocation();
            final String url = item.getHtmlUrl();
            setImageOfOrg(imageUri);
            setTitleOfOrg(login);
            setAuthorOfOrg(location);
            setUrlOfOrg(url);
        }
    }

    @OnClick(R.id.wrap)
    void click(){
        medalAnimation.startAnimation(OrganizationItemView.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(RepositoriesActivity.getNewIntent(getContext(), item.getLogin(), item.getPublicRepos()));
            }
        }, 1000);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}