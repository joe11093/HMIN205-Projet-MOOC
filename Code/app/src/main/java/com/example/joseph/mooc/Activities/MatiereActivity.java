package com.example.joseph.mooc.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joseph.mooc.Fragments.FicheDeCoursListFragment;
import com.example.joseph.mooc.Fragments.QcmListFragment;
import com.example.joseph.mooc.Fragments.QuestionsReponsesListeFragments;
import com.example.joseph.mooc.Fragments.VideoListFragment;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.FragmentCallback;
import com.example.joseph.mooc.Models.FicheDeCours;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

public class MatiereActivity extends AppCompatActivity implements FragmentCallback{
    /*String matiere;
    String matiere_id;
    String annee_id_of_matiere;*/
    public Matiere matiere;
    public FicheDeCours ficheDeCours;
    TextView matiereTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matiere);

        Log.d("MatiereActivity", "OnCreate");

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
        this.matiereTv.setText(this.matiere.getTitre());
        //this.matiereTv.setText("MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());
        Log.d("MatiereActivity", "MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());

    }

    public void setFicheDeCours(FicheDeCours ficheDeCours){
        this.ficheDeCours = ficheDeCours;
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
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


    public void replaceFragment(Fragment fragment) {
        Log.d("MatiereActivity", "replaceFragment function");
        Log.d("MatiereActivity", "Fragment class :" + fragment.getClass().toString());
        FragmentManager fragmentManager = getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activiteMatiereLayout, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}