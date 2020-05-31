package com.esiea.githubv.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.esiea.githubv.Singletons;
import com.esiea.githubv.model.User;
import com.esiea.githubv.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainController {

    private int currentPage;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
        this.currentPage=0;
    }

    public void onStart() {
        makeApiCallUserList(0);
    }

    public void nextPage() {
        currentPage=currentPage+20;
        makeApiCallUserList(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }
    public void previousPage() {
        if(currentPage!=0) {
            currentPage=currentPage-20;
            makeApiCallUserList(currentPage);
        }
        else {
            view.showError();
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
                    if(getDataFromCache()==null) {
                        saveList(userList);
                    }
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

    public void saveList(List<User> userList) {
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

}
