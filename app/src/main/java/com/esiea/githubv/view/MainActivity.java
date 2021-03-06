package com.esiea.githubv.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.esiea.githubv.Singletons;
import com.esiea.githubv.controller.MainController;
import com.esiea.githubv.model.BaseActivity;
import com.esiea.githubv.model.ListUserAdapter;
import com.esiea.githubv.R;
import com.esiea.githubv.model.User;

import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ListUserAdapter mUserAdapter;
    private SearchView searchView;
    private View favoris;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;
    private Button page_precedente;
    private Button page_suivante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())
        );
        controller.onStart();
        showPageButton();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                controller.makeApiCallFindUser(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.show_global) {
            controller.makeApiCallUserList(controller.getCurrentPage());
        }
        if(id == R.id.show_favoris) {
            showFavoriteUserList();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showUserList(List<User> userList) {
        page_suivante.setVisibility(View.VISIBLE);
        page_precedente.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mUserAdapter = new ListUserAdapter(userList, new ListUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mUserAdapter);
    }

    public void showFavoriteUserList() {
        page_suivante.setVisibility(View.INVISIBLE);
        page_precedente.setVisibility(View.INVISIBLE);
        final List<User> favoriteUserList = controller.getDataFromCache();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mUserAdapter = new ListUserAdapter(favoriteUserList, new ListUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mUserAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(User user) {
        Intent myIntent = new Intent(MainActivity.this, RepoListActivity.class);
        myIntent.putExtra("login", user.getLogin());
        myIntent.putExtra("id", user.getId());
        myIntent.putExtra("avatar_url", user.getAvatar_url());
        MainActivity.this.startActivity(myIntent);
    }

    public void showPageButton() {
        page_suivante = findViewById(R.id.page_suivante);
        page_suivante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.nextPage();
            }
        });

        page_precedente = findViewById(R.id.page_precedente);
        page_precedente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.previousPage();
            }
        });
    }
}
