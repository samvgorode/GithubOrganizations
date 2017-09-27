package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.adapters.RepositoriesAdapter;
import com.example.who.githuborganizations.interfaces.IRepositoriesView;
import com.example.who.githuborganizations.pojo.Repository;
import com.example.who.githuborganizations.presenters.RepositoriesActivityPresenter;
import com.example.who.githuborganizations.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesActivity extends AppCompatActivity implements IRepositoriesView {


    @BindView(R.id.rvRepositories)
    public RecyclerView rvRepositories;
    @BindView(R.id.tvLabel)
    public TextView tvLabel;
    @BindView(R.id.pbProgress)
    public ProgressBar pbProgress;
    @BindView(R.id.ivNoResults)
    public ImageView ivNoResults;
    @BindView(R.id.rlWrapper)
    public RelativeLayout rlWrapper;
    public RepositoriesActivityPresenter presenter;
    public RepositoriesAdapter adapter;

    private static final String ORG_LOGIN = "ORG_LOGIN";
    private static final String NUM_OF_REPOS = "NUM_OF_REPOS";

    public static Intent getNewIntent(Context context, String orgLogin, Integer numOfRepos) {
        Intent intent = new Intent(context, RepositoriesActivity.class);
        intent.putExtra(ORG_LOGIN, orgLogin);
        intent.putExtra(NUM_OF_REPOS, numOfRepos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositores);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkConnected()) DialogUtils.showInternetAlertDialog(this);
        else {
            adapter = new RepositoriesAdapter(this);
            presenter = new RepositoriesActivityPresenter(RepositoriesActivity.this, this);
            initRecyclerView();
            init();
        }
    }

    private void init() {
        Intent intent = getIntent();
        if (intent.hasExtra(ORG_LOGIN) && intent.hasExtra(NUM_OF_REPOS)) {
            String orgName = intent.getStringExtra(ORG_LOGIN);
            presenter.showOrganizations(orgName);
            Integer numOfRepos = intent.getIntExtra(NUM_OF_REPOS, 0);
            String reposNum = String.valueOf(numOfRepos);
            String title = orgName + " repositories " + "(" + reposNum + ")";
            tvLabel.setText(title);
        }
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvRepositories.getContext(),
                layoutManager.getOrientation());
        rvRepositories.addItemDecoration(dividerItemDecoration);
        rvRepositories.setLayoutManager(layoutManager);
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ivArrow)
    void click() {
        onBackPressed();
    }

    @Override
    public void setDataToAdapter(List<Repository> data) {
        adapter.setData(data);
        rvRepositories.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void noResults() {
        ivNoResults.setVisibility(View.VISIBLE);
        rlWrapper.setVisibility(View.GONE);
        rvRepositories.setVisibility(View.GONE);
    }

    @Override
    public void hasResults() {
        ivNoResults.setVisibility(View.GONE);
        rlWrapper.setVisibility(View.VISIBLE);
        rvRepositories.setVisibility(View.VISIBLE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
