package com.esiea.githubv;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitApi {

    @GET("/users/{user}")
    Call<RestUserResponse> getUserInformations(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<RestUserResponse> getListRepos(@Path("user") String user);

}
