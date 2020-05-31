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
import java.util.Iterator;
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

    public List<User> getDataFromCache() {
        String jsonFavorite = sharedPreferences.getString("jsonFavoriteUserList", null);
        if(jsonFavorite == null) {
            Toast.makeText(view.getApplicationContext(), "JSON load error", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Type listType = new TypeToken<List<User>>() {
            }.getType();
            return gson.fromJson(jsonFavorite, listType);
        }
    }

    public boolean inFav(String login) {
        boolean inFav=false;
        List<User> favList = getDataFromCache();
        Iterator<User> iterator = favList.iterator();
        while(iterator.hasNext())
        {
            String value = iterator.next().getLogin();
            if (login.equals(value))
            {
                inFav=true;
                break;
            }
        }
        return inFav;
    }

    public void addToFav(User user) {
        List<User> favList = getDataFromCache();
        favList.add(user);
        saveList(favList);
    }

    public void removeToFav(User user) {
        List<User> favList = getDataFromCache();
        Iterator<User> iterator = favList.iterator();
        while(iterator.hasNext())
        {
            String value = iterator.next().getLogin();
            if (user.getLogin().equals(value))
            {
                iterator.remove();
                break;
            }
        }
        saveList(favList);
    }

    public void saveList(List<User> userList) {
        String jsonString = gson.toJson(userList);
        sharedPreferences
                .edit()
                .putString("jsonFavoriteUserList", jsonString)
                .apply();
        Toast.makeText(view.getApplicationContext(), "JSON saved", Toast.LENGTH_SHORT).show();
    }

    public void onItemClick(User user) {
        //view.navigateToDetails(user);
    }

}
