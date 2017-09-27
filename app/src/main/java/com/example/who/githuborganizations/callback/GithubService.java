package com.example.who.githuborganizations.callback;

import com.example.who.githuborganizations.pojo.Organization;
import com.example.who.githuborganizations.pojo.Repository;
import com.example.who.githuborganizations.pojo.UserOrganizations;
import com.example.who.githuborganizations.pojo.UserOrganization;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.value;

/**
 * Created by who on 26.09.2017.
 */

public interface GithubService {
    @GET("search/users")
    Call<UserOrganizations> getUserOrganizations(@Header("Authorization") String token, @Header("Accept") String accept, @Query(value = "q", encoded = true) String search);

    @GET("/orgs/{org}")
    Call<Organization> getOrganization(@Header("Authorization") String token, @Header("Accept") String accept, @Path("org") String orgname);

    @GET("users/{owner}/repos")
    Call<List<Repository>> getRepositories(@Header("Authorization") String token, @Header("Accept") String accept, @Path("owner") String owner);
}
