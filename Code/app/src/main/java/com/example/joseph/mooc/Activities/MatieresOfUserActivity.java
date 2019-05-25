package com.example.joseph.mooc.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.joseph.mooc.BackgroundTasks.GetMatiereOfAnneeScolaireTask;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Helper.MatiereArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatieresOfUserActivity extends AppCompatActivity implements Callback{
    RecyclerView recyclerView;
    SharedPreferences prefs;
    String annee_id;
    ArrayList<Matiere> listMatieres = new ArrayList<Matiere>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matieres_of_user);

        prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        String loggedIn_annee_id = prefs.getString("annee_id", null);
        String loggedIn_annee_scolaire = prefs.getString("annee_scolaire", null);
        this.annee_id = loggedIn_annee_id;

        //get all matieres for the corresponding annee scolaire of this user
        GetMatiereOfAnneeScolaireTask getMatiereOfAnneeScolaireTask = new GetMatiereOfAnneeScolaireTask(this);
        getMatiereOfAnneeScolaireTask.execute(loggedIn_annee_id);



    }

    public void initializeRecyclerView(){
        //init recyclerview with the matierearrayadapter
        this.recyclerView = findViewById(R.id.recyclerMatieres);
        MatiereArrayAdapter matiereArrayAdapter = new MatiereArrayAdapter(R.layout.matiere_list_item, this.listMatieres);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(matiereArrayAdapter);


    }
    @Override
    public void processData(String code, String data) {
        if(code.equals("VideoListMatiereTask")){
            if(!data.equals("no_results") && !data.equals("error")){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i =  0; i < jsonArray.length(); i++){
                        JSONObject matiere = jsonArray.getJSONObject(i);
                        this.listMatieres.add(new Matiere(matiere.getString("id"), matiere.getString("titre"),  matiere.getString("annee_id")));
                    }
                    Log.d("UserProfileActivity", "VideoListMatiereTask: printing first element of returned json: " + listMatieres.get(0).getId() + ": " +  listMatieres.get(0).getTitre());

                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
