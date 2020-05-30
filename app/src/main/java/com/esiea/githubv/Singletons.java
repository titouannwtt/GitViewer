package com.esiea.githubv;

import android.content.Context;
import android.content.SharedPreferences;

import com.esiea.githubv.data.GitApi;
import com.esiea.githubv.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static GitApi gitApiInstance;
    private static SharedPreferences sharedPreferencesInstance;
    private static User currentUser;


    public static Gson getGson() {
        if(gsonInstance == null ) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser=user;
    }

    public static GitApi getGitApi() {
        if(gitApiInstance==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            gitApiInstance = retrofit.create(GitApi.class);
        }
        return gitApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferencesInstance == null ) {
            sharedPreferencesInstance = context.getSharedPreferences("favorite", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
