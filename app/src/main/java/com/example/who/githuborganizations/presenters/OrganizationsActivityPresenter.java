package com.example.who.githuborganizations.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.Log;

import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.interfaces.IOrganizationsView;
import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.UserOrganization;
import com.example.who.githuborganizations.pojo.UserOrganizations;
import com.example.who.githuborganizations.utils.TokenUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.internal.observers.SubscriberCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
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
    private DisposableObserver userOrganizations;
    private DisposableObserver organizations;

    public OrganizationsActivityPresenter(Context context, IOrganizationsView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String search) {
        view.showProgress();
        cancelCallbacks();
        String query = search + "+in:login+type:org";
        userOrganizations = mManager.getGithubService().getUserOrganizations(token, ACCEPT_HEADER, query.trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribeWith(new DisposableObserver<UserOrganizations>() {
                    @Override
                    public void onNext(UserOrganizations value) {
                        if (!value.getItems().isEmpty()) {
                            data.addAll(value.getItems());
                            setData();
                            view.hideProgress();
                        } else view.noResults();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.noResults();
                        view.hideProgress();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void setData() {
        final List<Organization> dataOrg = new ArrayList<>(data.size());
        for (int i = 0; i < data.size(); i++) {
            organizations = mManager.getGithubService().getOrganization(token, ACCEPT_HEADER, data.get(i).getLogin())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .cache()
                    .subscribeWith(new DisposableObserver<Organization>() {
                        @Override
                        public void onNext(Organization value) {
                            view.hasResults();
                            dataOrg.add(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.noResults();
                            view.hideProgress();
                        }

                        @Override
                        public void onComplete() {
                            view.setDataToAdapter(dataOrg);
                            view.hideProgress();
                        }
                    });
        }
    }

    private void cancelCallbacks() {
        if (userOrganizations != null) userOrganizations.dispose();
        if (organizations != null) organizations.dispose();
        if(data!=null) data.clear();
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
