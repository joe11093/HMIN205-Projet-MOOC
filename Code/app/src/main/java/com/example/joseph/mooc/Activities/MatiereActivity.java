package com.example.joseph.mooc.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class MatiereActivity extends AppCompatActivity {
    String matiere;
    String annee_id;
    TextView matiereTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere);

        this.matiereTv = findViewById(R.id.testMatiereFromRecycler);
        Bundle bundle = getIntent().getExtras();
        this.matiere = bundle.getString("matiere");

        SharedPreferences prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        this.annee_id = prefs.getString("annee_id", null);

        this.matiereTv.setGravity(Gravity.CENTER);
        this.matiereTv.setTextSize(50);
        this.matiereTv.setText(this.matiere);


    }

}
