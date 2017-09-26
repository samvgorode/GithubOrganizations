package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.adapters.OrganizationsAdapter;
import com.example.who.githuborganizations.iinterfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.presenters.OrganizationsActivityPresenter;
import com.example.who.githuborganizations.utils.TokenUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrganizationsActivity extends AppCompatActivity implements IOrganizationsView {

    private OrganizationsActivityPresenter presenter;

    @BindView(R.id.rvOrganizations)
    public RecyclerView rvOrganizations;
    @BindView(R.id.etSearch)
    public EditText etSearch;

    public OrganizationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!isNetworkConnected())
            Toast.makeText(this, "Switch on your internet!", Toast.LENGTH_LONG).show();
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
                if (editable.toString().length() >= 3) {
                    presenter.saveSearch(editable.toString());
                    presenter.showOrganizations(editable.toString());
                }
            }
        });
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBack() {

    }

    @Override
    public void setDataToAdapter(List<Organization> data) {
        adapter = new OrganizationsAdapter(this, data);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (rvOrganizations != null) {
            rvOrganizations.setLayoutManager(layoutManager);
            rvOrganizations.setAdapter(adapter);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
