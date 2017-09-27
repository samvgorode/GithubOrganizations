package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.adapters.OrganizationsAdapter;
import com.example.who.githuborganizations.iinterfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.presenters.OrganizationsActivityPresenter;
import com.example.who.githuborganizations.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationsActivity extends AppCompatActivity implements IOrganizationsView {

    private OrganizationsActivityPresenter presenter;

    @BindView(R.id.rvOrganizations)
    public RecyclerView rvOrganizations;
    @BindView(R.id.etSearch)
    public EditText etSearch;
    @BindView(R.id.pbProgress)
    public ProgressBar pbProgress;

    public OrganizationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkConnected())
            DialogUtils.showInternetAlertDialog(this);
        adapter = new OrganizationsAdapter(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOrganizations.setLayoutManager(layoutManager);
        init();
    }

    private void init() {
        presenter = new OrganizationsActivityPresenter(OrganizationsActivity.this, this);
        String savedSearch = presenter.getSavedSearch();
        if (savedSearch != null) {
            etSearch.setText(savedSearch);
            presenter.showOrganizations(savedSearch);
        }
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etSearch.getText().toString().length() >= 3) {
                    presenter.showOrganizations(etSearch.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        presenter.saveSearch(etSearch.getText().toString());
        super.onPause();
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDataToAdapter(List<Organization> data) {
        adapter.setData(data);
        rvOrganizations.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbProgress.setVisibility(View.GONE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
