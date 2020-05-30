package com.esiea.githubv.controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.esiea.githubv.Singletons;
import com.esiea.githubv.model.Repo;
import com.esiea.githubv.model.User;
import com.esiea.githubv.view.MainActivity;
import com.esiea.githubv.view.RepoListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoListController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private RepoListActivity view;
    private User user;

    public RepoListController(RepoListActivity repoListActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = repoListActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart() {

    }

    public void makeApiCallRepoListOfUser(User user) {
        //=========== RECUPERATION DES REPOS
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


    public void getUserByLogin(String login) {
        Call<User> callUser = Singletons.getGitApi().getUserInformation(login);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null) {
                    User newuser = response.body();
                    makeApiCallRepoListOfUser(newuser);
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


    public void onItemClick(User user) {
        //view.navigateToDetails(user);
    }

}
