package com.example.who.githuborganizations.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.iinterfaces.IOrganizationsView;
import com.example.who.githuborganizations.iinterfaces.IRepositoriesView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.Repository;
import com.example.who.githuborganizations.pojo.UserOrganization;
import com.example.who.githuborganizations.pojo.UserOrganizations;
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
    private Call<List<Repository>> orgUserCall;

    public RepositoriesActivityPresenter(Context context, IRepositoriesView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String orgLogin) {
        orgUserCall = mManager.getGithubService().getRepositories(token, ACCEPT_HEADER, orgLogin);
        orgUserCall.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(@NonNull Call<List<Repository>> call, @NonNull Response<List<Repository>> response) {
                if (response.body() != null) {
                    data = response.body();
                    view.setDataToAdapter(data);
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }
}
