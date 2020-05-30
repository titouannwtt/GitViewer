package com.esiea.githubv.model;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public void showBaseError() {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show();
    }
}
