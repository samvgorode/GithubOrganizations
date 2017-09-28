package com.example.who.githuborganizations.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.adapters.OrganizationsAdapter;
import com.example.who.githuborganizations.interfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.presenters.OrganizationsActivityPresenter;
import com.example.who.githuborganizations.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationsActivity extends AppCompatActivity implements IOrganizationsView, View.OnTouchListener {


    @BindView(R.id.rvOrganizations)
    public RecyclerView rvOrganizations;
    @BindView(R.id.etSearch)
    public EditText etSearch;
    @BindView(R.id.pbProgress)
    public ProgressBar pbProgress;
    @BindView(R.id.ivDelete)
    public ImageView ivDelete;
    @BindView(R.id.ivNoResults)
    public ImageView ivNoResults;

    public Handler handler = new Handler();

    private OrganizationsActivityPresenter presenter;
    public OrganizationsAdapter adapter;

    public static Intent getNewIntent(Context context) {
        return new Intent(context, OrganizationsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizations);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        adapter = new OrganizationsAdapter(this);
        presenter = new OrganizationsActivityPresenter(OrganizationsActivity.this, this);
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkConnected()) DialogUtils.showInternetAlertDialog(this);
        else init();
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvOrganizations.getContext(),
                layoutManager.getOrientation());
        rvOrganizations.addItemDecoration(dividerItemDecoration);
        rvOrganizations.setLayoutManager(layoutManager);
        rvOrganizations.setOnTouchListener(this);
    }

    private void init() {

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
                if (TextUtils.isEmpty(etSearch.getText())) ivDelete.setVisibility(View.GONE);
                else ivDelete.setVisibility(View.VISIBLE);
                if (!isNetworkConnected())
                    DialogUtils.showInternetAlertDialog(OrganizationsActivity.this);
                else {
                    handler.removeCallbacksAndMessages(null);
                    if (etSearch.getText().toString().length() >= 3) {
                        hasResults();
                        showProgress();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.showOrganizations(etSearch.getText().toString());
                                hideProgress();
                            }
                        }, 2000);

                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        presenter.saveSearch(etSearch.getText().toString());
        super.onPause();
    }

    @OnClick(R.id.ivDelete)
    void clickDelete() {
        etSearch.setText("");
    }

    @OnClick(R.id.ivNoResults)
    void clickNoResults() {
        hideSoftKeyboard(this);
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

    @Override
    public void noResults() {
        rvOrganizations.setVisibility(View.GONE);
        ivNoResults.setVisibility(View.VISIBLE);
    }

    @Override
    public void hasResults() {
        rvOrganizations.setVisibility(View.VISIBLE);
        ivNoResults.setVisibility(View.GONE);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        hideSoftKeyboard(this);
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
