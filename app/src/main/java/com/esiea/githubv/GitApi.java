package com.esiea.githubv;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitApi {

    @GET("/users/{user}/repos")
    Call<List<Repo>> repoUser(
            @Path("user") String user
    );

    @GET("users")
    Call<List<User>> getUserList(
            @Query("per_page") String peerage,
            @Query("since") String page
    );

    @GET("/users/{user}")
    Call<User> getUserInformation(
            @Path("user") String user
    );

}
