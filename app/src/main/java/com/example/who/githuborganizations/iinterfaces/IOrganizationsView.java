package com.example.who.githuborganizations.iinterfaces;

import com.example.who.githuborganizations.pojo.Organization;

import java.util.List;

/**
 * Created by who on 26.09.2017.
 */

public interface IOrganizationsView {

    void updateAdapter();

    void setDataToAdapter(List<Organization> data);

    void showProgress();

    void hideProgress();

}
