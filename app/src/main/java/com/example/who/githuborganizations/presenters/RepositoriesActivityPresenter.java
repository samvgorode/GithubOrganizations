package com.example.who.githuborganizations.presenters;

import android.content.Context;
import com.example.who.githuborganizations.controller.RestManager;
import com.example.who.githuborganizations.interfaces.IRepositoriesView;
import com.example.who.githuborganizations.pojo.Repository;
import com.example.who.githuborganizations.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.who.githuborganizations.global.Constants.ACCEPT_HEADER;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesActivityPresenter {
    ;
    private RestManager mManager;
    private IRepositoriesView view;
    private Context context;
    private final String token = TokenUtils.getMyToken();
    private DisposableObserver repository;

    public RepositoriesActivityPresenter(Context context, IRepositoriesView view) {
        this.view = view;
        this.context = context;
        mManager = new RestManager();
    }

    public void showOrganizations(String orgLogin) {
        view.showProgress();
        if(repository!=null) repository.dispose();
        List<Repository> data = new ArrayList<>();
        repository = mManager.getGithubService().getRepositories(token, ACCEPT_HEADER, orgLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .subscribeWith(new DisposableObserver<List<Repository>>(){
                    @Override
                    public void onNext(List<Repository> value) {
                        if(!value.isEmpty()){
                            data.addAll(value);
                            view.setDataToAdapter(data);
                            view.hideProgress();
                        }
                        else view.noResults();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
