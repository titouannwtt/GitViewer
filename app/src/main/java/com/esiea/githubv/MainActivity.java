package com.esiea.githubv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends BaseActivity implements MainInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
