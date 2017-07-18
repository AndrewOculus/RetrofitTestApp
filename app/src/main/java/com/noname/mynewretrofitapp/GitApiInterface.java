package com.noname.mynewretrofitapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitApiInterface {

    @GET("/users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);
}