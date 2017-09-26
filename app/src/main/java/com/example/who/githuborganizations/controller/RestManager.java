package com.example.who.githuborganizations.controller;

import com.example.who.githuborganizations.callback.GithubService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by who on 26.09.2017.
 */

public class RestManager {

    GithubService mPollutionService;
    public static final String API_BASE_URL = "https://api.github.com/";

    public GithubService getGithubService() {
        if (mPollutionService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mPollutionService = retrofit.create(GithubService.class);
        }

        return mPollutionService;
    }
}
