package com.esiea.githubv.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.esiea.githubv.Constants;
import com.esiea.githubv.Singletons;
import com.esiea.githubv.data.GitApi;
import com.esiea.githubv.model.Repo;
import com.esiea.githubv.model.User;
import com.esiea.githubv.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart() {
        makeApiCallUserList(0);

    }

    public List<User> getDataFromCache() {
        String jsonFavorite = sharedPreferences.getString("jsonFavoriteUserList", null);
        if(jsonFavorite == null) {
            Toast.makeText(view.getApplicationContext(), "JSON load error", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Toast.makeText(view.getApplicationContext(), "JSON loaded", Toast.LENGTH_SHORT).show();
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(jsonFavorite, listType);
        }
    }


    public void makeApiCallUserList(int page) {
        //=========== RECUPERATION DE TOUS LES UTILISATEURS GITHUB
        Call<List<User>> callUserlist = Singletons.getGitApi().getUserList("20", String.valueOf(page));
        callUserlist.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body();
                    saveList(userList);
                    view.showUserList(userList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                view.showError();
            }
        });
    }

    public void makeApiCallRepoListOfUser(User user) {
        //=========== RECUPERATION DE REPO
        Call<List<Repo>> callRepo = Singletons.getGitApi().repoUser(user.getLogin());
        callRepo.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Repo> repos = response.body();
                    view.showRepoList(repos);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                view.showError();
            }
        });
    }

    public void makeApiCallFindUser(String login) {
        //=========== RECUPERATION DE UN UTILISATEUR GITHUB
        Call<User> callUser = Singletons.getGitApi().getUserInformation(login);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    List<User> userList = new ArrayList<>();
                    userList.add(user);
                    view.showUserList(userList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<User> userList) {
        String jsonString = gson.toJson(userList);
        sharedPreferences
                .edit()
                .putString("jsonFavoriteUserList", jsonString)
                .apply();
        Toast.makeText(view.getApplicationContext(), "JSON saved", Toast.LENGTH_SHORT).show();
    }

    public void onItemClick(User user) {
        view.navigateToDetails(user);
    }

    public void onButtonAClick() {

    }
}
