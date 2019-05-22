package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mooc.BackgroundTasks.GetMatiereOfAnneeScolaireTask;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;
import com.example.joseph.mooc.Interfaces.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends AppCompatActivity implements Callback{
    String userId, firstname, annee_id, annee_scolaire;
    TextView topOfPageText;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Log.d("UserProfileActivity", "User profile on create");

        prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        String loggedInId = prefs.getString("id", null);
        String loggedInFirstName = prefs.getString("firstname", null);
        String loggedIn_annee_id = prefs.getString("annee_id", null);
        String loggedIn_annee_scolaire = prefs.getString("annee_scolaire", null);

        this.userId = loggedInId;
        this.firstname = loggedInFirstName;
        this.annee_id = loggedIn_annee_id;
        this.annee_scolaire = loggedIn_annee_scolaire;

        this.topOfPageText = findViewById(R.id.userProfileTopOfPage);
        topOfPageText.setText("Hello " + this.firstname + ", this is your profile page. Your school year is: " + this.annee_scolaire);


        //get all matieres for the corresponding annee scolaire of this user
        GetMatiereOfAnneeScolaireTask getMatiereOfAnneeScolaireTask = new GetMatiereOfAnneeScolaireTask(this);
        getMatiereOfAnneeScolaireTask.execute(this.annee_id);

    }

    public void logout(View view) {
        SharedPreferences.Editor editor =prefs.edit();

        editor.remove("id");
        editor.remove("firstname");
        editor.remove("email");
        editor.remove("type");
        editor.remove("annee_id");
        editor.remove("annee_scolaire");

        editor.apply();

        Toast.makeText(this, R.string.logoutMessage, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
    }

    @Override
    public void processData(String code, String data) {
        if(code.equals("getmatiereanneeTask")){
            if(!data.equals("no_results")){
                try {
                JSONArray jsonArray = new JSONArray(data);
                Matiere[] matiereArray = new Matiere[jsonArray.length()];
                    for(int i =  0; i < jsonArray.length(); i++){
                        JSONObject matiere = jsonArray.getJSONObject(i);
                        matiereArray[i] = new Matiere(matiere.getInt("id"), matiere.getString("titre"), this.annee_id);
                    }
                    Log.d("UserProfileActivity", "getmatiereanneetask: printing first element of returned json: " + matiereArray[0].getId() + ": " + matiereArray[0].getTitre());
                    //set the adapter

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
