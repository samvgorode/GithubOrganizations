package com.example.who.githuborganizations.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.interfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.UserOrganization;
import com.example.who.githuborganizations.pojo.UserOrganizations;
import com.example.who.githuborganizations.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.who.githuborganizations.global.Constants.ACCEPT_HEADER;
import static com.example.who.githuborganizations.global.Constants.LAST_SEARCH;

/**
 * Created by who on 26.09.2017.
 */

public class OrganizationsActivityPresenter {

    private List<UserOrganization> data = new ArrayList<>();
    private RestManager mManager;
    private IOrganizationsView view;
    private Context context;
    private final String token = TokenUtils.getMyToken();
    private Call<UserOrganizations> orgUserCall;
    private Call<Organization> orgCall;

    public OrganizationsActivityPresenter(Context context, IOrganizationsView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String search) {
        view.showProgress();
        cancelCallbacks();
        String query = search + "+in:login+type:org";
        orgUserCall = mManager.getGithubService().getUserOrganizations(token, ACCEPT_HEADER, query.trim());
        orgUserCall.enqueue(new Callback<UserOrganizations>() {
            @Override
            public void onResponse(@NonNull Call<UserOrganizations> call, @NonNull Response<UserOrganizations> response) {
                if (data.size() > 0) data.clear();
                if (response.body() != null) {
                    if (response.body().getItems().size() > 0) {
                        data = response.body().getItems();
                        if (data.size() > 0) setData();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserOrganizations> call, Throwable t) {
                view.hideProgress();
            }
        });
    }

    private void setData() {
        final List<Organization> dataOrg = new ArrayList<>(data.size());

        for (int i = 0; i < data.size(); i++) {
            orgCall = mManager.getGithubService().getOrganization(token, ACCEPT_HEADER, data.get(i).getLogin());
            orgCall.enqueue(new Callback<Organization>() {
                @Override
                public void onResponse(Call<Organization> call, Response<Organization> response) {
                    if (response.body() != null) {
                        dataOrg.add(response.body());
                        view.setDataToAdapter(dataOrg);
                        view.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<Organization> call, Throwable t) {
                    view.hideProgress();
                }
            });
        }
    }

    private void cancelCallbacks() {
        if (orgUserCall != null && !orgUserCall.isCanceled() && orgUserCall.isExecuted())
            orgUserCall.cancel();
        if (orgCall != null && !orgCall.isCanceled() && orgCall.isExecuted()) orgCall.cancel();
    }


    public void saveSearch(String search) {
        SharedPreferences.Editor editor = context.getSharedPreferences(LAST_SEARCH, MODE_PRIVATE).edit();
        editor.putString(LAST_SEARCH, search);
        editor.apply();
    }

    public String getSavedSearch() {
        String restoredValue = null;
        SharedPreferences prefs = context.getSharedPreferences(LAST_SEARCH, MODE_PRIVATE);
        String restoredText = prefs.getString(LAST_SEARCH, null);
        if (restoredText != null) {
            restoredValue = prefs.getString(LAST_SEARCH, "No value");//"No name defined" is the default value.
        }
        return restoredValue;
    }
}
