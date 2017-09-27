package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
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
    public RepositoriesActivityPresenter presenter;
    public RepositoriesAdapter adapter;

    private static final String ORG_LOGIN = "ORG_LOGIN";
    private Repository item;

    public static Intent getNewIntent(Context context, String orgLogin) {
        Intent intent = new Intent(context, RepositoriesActivity.class);
        intent.putExtra(ORG_LOGIN, orgLogin);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositores);
        ButterKnife.bind(this);
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
        if (intent.hasExtra(ORG_LOGIN)) {
            String orgName = intent.getStringExtra(ORG_LOGIN);
            presenter.showOrganizations(orgName);
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

    @Override
    public void onBack() {

    }

    @Override
    public void setDataToAdapter(List<Repository> data) {
        adapter.setData(data);
        rvRepositories.setAdapter(adapter);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
