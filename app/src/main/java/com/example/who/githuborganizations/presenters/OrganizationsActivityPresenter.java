package com.example.who.githuborganizations.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.iinterfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.Organizations;
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

    private List<Organization> data = new ArrayList<>();
    private RestManager mManager;
    private IOrganizationsView view;
    private Context context;

    public OrganizationsActivityPresenter(Context context, IOrganizationsView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String search) {
        String token = TokenUtils.getMyToken();
        String query = search +"+in:login+type:org";
        Call<Organizations> orgCall = mManager.getGithubService().getOrganizations(token, ACCEPT_HEADER, search);
        orgCall.enqueue(new Callback<Organizations>() {
            @Override
            public void onResponse(@NonNull Call<Organizations> call, @NonNull Response<Organizations> response) {
                if (response.body() != null) {
                    if (response.body().getItems().size() > 0) {
                        data = response.body().getItems();
                        view.setDataToAdapter(data);
                    }
                }
            }

            @Override
            public void onFailure(Call<Organizations> call, Throwable t) {

            }
        });
    }


    public void saveSearch(String search){
        SharedPreferences.Editor editor = context.getSharedPreferences(LAST_SEARCH, MODE_PRIVATE).edit();
        editor.putString(LAST_SEARCH, search);
        editor.apply();
    }

    public String getSavedSearch(){
        String restoredValue = null;
        SharedPreferences prefs = context.getSharedPreferences(LAST_SEARCH, MODE_PRIVATE);
        String restoredText = prefs.getString(LAST_SEARCH, null);
        if (restoredText != null) {
            restoredValue = prefs.getString(LAST_SEARCH, "No value");//"No name defined" is the default value.
        } return restoredValue;
    }

}
