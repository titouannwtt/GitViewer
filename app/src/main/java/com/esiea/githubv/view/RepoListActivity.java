package com.esiea.githubv.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.esiea.githubv.Singletons;
import com.esiea.githubv.controller.MainController;
import com.esiea.githubv.controller.RepoListController;
import com.esiea.githubv.model.BaseActivity;
import com.esiea.githubv.model.ListRepoAdapter;
import com.esiea.githubv.model.ListUserAdapter;
import com.esiea.githubv.R;
import com.esiea.githubv.model.Repo;
import com.esiea.githubv.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListRepoAdapter mRepoAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RepoListController controller;
    private User user;

    private TextView loginName;
    private ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repolist_activity);

        //On récupère l'utilisateur pour lequel on souhaite afficher la liste des repos
        Intent intent = getIntent();
        String login = intent.getStringExtra("login");
        String id = intent.getStringExtra("id");
        String avatar_url = intent.getStringExtra("avatar_url");
        controller = new RepoListController(this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller.onStart();
        //controller.getUserById(id);
        controller.getUserByLogin(login);


        //Initialisation graphique
        loginName = findViewById(R.id.loginName);
        imgIcon = findViewById(R.id.icon);
        Picasso.get().load(avatar_url).into(imgIcon);
        loginName.setText(login);

        /*Button favoris_button = findViewById(R.id.favoris_button);
        favoris_button.setBackgroundColor(Color.RED);
        favoris_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "OnClicked", Toast.LENGTH_LONG).show();
            }
        });*/
    }


    public void showRepoList(List<Repo> repos) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_repo);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mRepoAdapter = new ListRepoAdapter(repos);
        recyclerView.setAdapter(mRepoAdapter);
    }

    public void showError() {
        //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }


}
