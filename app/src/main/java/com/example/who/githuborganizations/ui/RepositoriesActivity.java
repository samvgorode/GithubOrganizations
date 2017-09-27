package com.example.who.githuborganizations.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.who.githuborganizations.R;
import com.example.who.githuborganizations.iinterfaces.IRepositoriesView;
import com.example.who.githuborganizations.pojo.Repository;

import java.util.List;

import butterknife.BindView;

/**
 * Created by who on 27.09.2017.
 */

public class RepositoriesActivity extends AppCompatActivity implements IRepositoriesView {

    private static final String ORG_LOGIN = "ORG_LOGIN";
    private Repository item;

    public static Intent getNewIntent(Context context, String orgLogin) {
        Intent intent = new Intent(context, RepositoriesActivity.class);
        intent.putExtra(ORG_LOGIN, orgLogin);
        return intent;
    }


    @Override
    public void updateAdapter() {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void setDataToAdapter(List<Repository> data) {

    }
}
