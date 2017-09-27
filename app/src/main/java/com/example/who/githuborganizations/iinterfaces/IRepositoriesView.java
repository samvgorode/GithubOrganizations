package com.example.who.githuborganizations.iinterfaces;

import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.Repository;

import java.util.List;

/**
 * Created by who on 27.09.2017.
 */

public interface IRepositoriesView {
    void updateAdapter();

    void onBack();

    void setDataToAdapter(List<Repository> data);
}
