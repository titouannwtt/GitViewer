package com.esiea.githubv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity implements MainInterface {

    private String user = null;
    private static final String url = "https://api.github.com";
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeApiCall();
    }

    /*public void showInterfaceExample() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<String> input = new ArrayList<>();
        for(int j=0; j++; j==100)
        {
            input.add("Exemple" + j);
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);

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
                        input.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
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
    }*/

    @Override
    public void showRepoList(List<Repo> repos) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<String> input = new ArrayList<>();
        for(Repo elem: repos)
        {
            input.add("Nom:" + elem.getName());
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showUserList(List<User> userList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<String> input = new ArrayList<>();
        for(User elem: userList)
        {
            input.add("Nom:" + elem.getLogin());
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void makeApiCall() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitApi gitApi = retrofit.create(GitApi.class);

        /*
        //=========== RECUPERATION DE REPO
        user = "titouannwtt";
        Call<List<Repo>> callRepo = gitApi.repoUser(user);
        callRepo.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<Repo> repos = response.body();
                    showRepoList(repos);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                showError();
            }
        });
        //=====================================
         */


        //=========== RECUPERATION DES UTILISATEURS
        Call<List<User>> callUserlist = gitApi.getUserList("20", "100" );
        callUserlist.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> callUserlist, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<User> userList = response.body();
                    showUserList(userList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<User>> callUserlist, Throwable t) {
                showError();
            }
        });

    }
}
