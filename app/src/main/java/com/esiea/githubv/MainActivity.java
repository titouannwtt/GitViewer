package com.esiea.githubv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainInterface {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }

        mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);

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

        Users user = new Users("titouannwtt", 63909350, "https://avatars2.githubusercontent.com/u/63909350?v=4");
        showBaseError();
    }

    @Override
    public void showList(List<Users> list) {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void showError() {

    }
}
