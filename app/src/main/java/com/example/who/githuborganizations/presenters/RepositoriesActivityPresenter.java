package com.example.who.githuborganizations.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.interfaces.IRepositoriesView;
import com.example.who.githuborganizations.pojo.Repository;
import com.example.who.githuborganizations.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.who.githuborganizations.global.Constants.ACCEPT_HEADER;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesActivityPresenter {

    private List<Repository> data = new ArrayList<>();
    private RestManager mManager;
    private IRepositoriesView view;
    private Context context;
    private final String token = TokenUtils.getMyToken();

    public RepositoriesActivityPresenter(Context context, IRepositoriesView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String orgLogin) {
        view.showProgress();
        Call<List<Repository>> orgUserCall = mManager.getGithubService().getRepositories(token, ACCEPT_HEADER, orgLogin);
        orgUserCall.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repository>> call, @NonNull Response<List<Repository>> response) {
                if (response.body() != null) {
                    if (response.body().size() > 0) {
                        view.hasResults();
                        data = response.body();
                        view.setDataToAdapter(data);
                        view.hideProgress();
                    } else view.noResults();
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                view.hideProgress();
                view.noResults();
            }
        });
    }
}
