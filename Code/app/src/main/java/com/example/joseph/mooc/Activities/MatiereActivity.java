package com.example.joseph.mooc.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.joseph.mooc.Fragments.FicheDeCoursListFragment;
import com.example.joseph.mooc.Fragments.QcmListFragment;
import com.example.joseph.mooc.Fragments.QuestionsReponsesListeFragments;
import com.example.joseph.mooc.Fragments.VideoListFragment;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

public class MatiereActivity extends AppCompatActivity {
    /*String matiere;
    String matiere_id;
    String annee_id_of_matiere;*/
    public Matiere matiere;
    TextView matiereTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere);

        this.matiereTv = findViewById(R.id.testMatiereFromRecycler);
        Bundle bundle = getIntent().getExtras();
        this.matiere = (Matiere) bundle.get("matiere");

        /*this.matiere = bundle.getString("matiere");
        this.matiere_id = bundle.getString("matiere_id");
        this.annee_id_of_matiere = bundle.getString("annee_id_of_matiere");*/

        //SharedPreferences prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        //this.annee_id = prefs.getString("annee_id", null);

        this.matiereTv.setGravity(Gravity.CENTER);
        this.matiereTv.setTextSize(50);
        this.matiereTv.setText("ID OF MATIERE: " + this.matiere.getId() + " MATIERE: " + this.matiere.getTitre() + " DE L'ANNEE " + this.matiere.getAnnee_id());
        Log.d("MatiereActivity", "MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void toListVideos(View view) {
        Fragment fragment = new VideoListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activiteMatiereLayout, fragment).addToBackStack(null).commit();
    }

    public void toListFicheDeCours(View view) {
        Fragment fragment = new FicheDeCoursListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activiteMatiereLayout, fragment).addToBackStack(null).commit();
    }

    public void toListQCM(View view) {
        Fragment fragment = new QcmListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activiteMatiereLayout, fragment).addToBackStack(null).commit();
    }

    public void toListQuestionsReponses(View view) {
        Fragment fragment = new QuestionsReponsesListeFragments();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.activiteMatiereLayout, fragment).addToBackStack(null).commit();
    }
}
