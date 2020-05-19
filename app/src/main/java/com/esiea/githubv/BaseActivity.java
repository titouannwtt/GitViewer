package com.esiea.githubv;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public void showBaseError() {
        //TODO Erreur
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
    }
}
