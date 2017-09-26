package com.example.who.githuborganizations.callback;

import com.example.who.githuborganizations.pojo.Organizations;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by who on 26.09.2017.
 */

public interface GithubService {
    @GET("search/users")
    Call<Organizations> getOrganizations(@Header("Authorization") String token, @Header("Accept") String accept, @Query("q") String search);

    @GET("users/{owner}/repos")
    Call<Organizations> getRepositories(@Header("Authorization") String token, @Header("Accept") String accept, @Path("owner") String owner);
}
