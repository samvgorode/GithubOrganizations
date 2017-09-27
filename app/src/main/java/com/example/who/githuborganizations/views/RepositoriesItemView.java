package com.example.who.githuborganizations.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.pojo.Repository;
import com.skydoves.medal.MedalAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesItemView extends RelativeLayout {

    @BindView(R.id.tvRepoName)
    TextView tvRepoName;
    @BindView(R.id.tvRepoDescription)
    TextView tvRepoDescription;

    private Repository item;
    private MedalAnimation medalAnimation;

    public RepositoriesItemView(Context context) {
        super(context);
        init();
    }

    public RepositoriesItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoriesItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.repository_item_view, this);
        ButterKnife.bind(this);
        initAnimator();
    }

    private void initAnimator() {
        medalAnimation = new MedalAnimation.Builder()
                .setDirection(MedalAnimation.RESTART)
                .setSpeed(1000)
                .setTurn(1)
                .setLoop(1)
                .build();
    }

    void setRepoName(String name) {
        tvRepoName.setText(name);
    }

    void setRepoDescription(String description) {
        tvRepoDescription.setText(description);
    }

    public void setItem(Repository item) {
        if (item != null) {
            RepositoriesItemView.this.item = item;
            final String repoName = item.getName();
            final String repoDescription = item.getDescription();
            setRepoName(repoName);
            setRepoDescription(repoDescription);
        }
    }

    @OnClick(R.id.wrap)
    void click() {
        medalAnimation.startAnimation(RepositoriesItemView.this);
    }
}
