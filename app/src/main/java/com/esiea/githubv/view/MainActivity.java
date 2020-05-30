package com.esiea.githubv.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.esiea.githubv.Singletons;
import com.esiea.githubv.controller.MainController;
import com.esiea.githubv.model.BaseActivity;
import com.esiea.githubv.model.ListRepoAdapter;
import com.esiea.githubv.model.ListUserAdapter;
import com.esiea.githubv.R;
import com.esiea.githubv.model.Repo;
import com.esiea.githubv.model.User;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends BaseActivity {

    private String user = null;
    private RecyclerView recyclerView;
    private ListUserAdapter mUserAdapter;
    private ListRepoAdapter mRepoAdapter;
    private SearchView searchView;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;

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
                //mAdapter.getFilter().filter(query);
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
        return super.onOptionsItemSelected(item);
    }


    public void showInterfaceExample() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*final List<String> input = new ArrayList<>();
        for(int j=0; j!=100; j++)
        {
            input.add("Exemple" + j);
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);*/

        //===========================
        //SWIPE

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //input.remove(viewHolder.getAdapterPosition());
                        mUserAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //==========================

        Button main_bouton = findViewById(R.id.button);
        main_bouton.setBackgroundColor(Color.RED);
        main_bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "OnClicked", Toast.LENGTH_LONG).show();
                TextView affichage = findViewById(R.id.textView2);
                affichage.setText("OK");
            }
        });
    }


    public void showRepoList(List<Repo> repos) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mRepoAdapter = new ListRepoAdapter(repos);
        recyclerView.setAdapter(mRepoAdapter);
    }


    public void showUserList(List<User> userList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mUserAdapter = new ListUserAdapter(userList);
        recyclerView.setAdapter(mUserAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "API Connexion error", Toast.LENGTH_SHORT).show();
    }

}
