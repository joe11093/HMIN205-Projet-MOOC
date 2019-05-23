package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.joseph.mooc.BackgroundTasks.GetMatiereOfAnneeScolaireTask;
import com.example.joseph.mooc.Helper.CheckConnectivity;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Helper.MatiereArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatieresOfUserActivity extends AppCompatActivity implements Callback, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    RecyclerView recyclerView;
    SharedPreferences prefs;
    String annee_id;
    ArrayList<Matiere> listMatieres = new ArrayList<Matiere>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("TEACHING");
        setContentView(R.layout.activity_matieres_of_user);

        registerReceiver(
                new CheckConnectivity(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

        prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        String loggedIn_annee_id = prefs.getString("annee_id", null);
        String loggedIn_annee_scolaire = prefs.getString("annee_scolaire", null);
        this.annee_id = loggedIn_annee_id;

        //get all matieres for the corresponding annee scolaire of this user
        GetMatiereOfAnneeScolaireTask getMatiereOfAnneeScolaireTask = new GetMatiereOfAnneeScolaireTask(this);
        getMatiereOfAnneeScolaireTask.execute(loggedIn_annee_id);


        Toolbar toolbar = findViewById(R.id.toolbar_matiere);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_matiere);
        NavigationView navigationView = findViewById(R.id.nav_view_matiere);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;
        int idItem = item.getItemId();
        Log.d("MatieresOfUserActivity","id: "+idItem);
        Log.d("MatieresOfUserActivity", "item.getItemId(): "+item.getItemId()+"");

        switch (item.getItemId()) {
            case R.id.nav_home_student:
                Log.d("MatieresOfUserActivity","R.id.nav_home_student_ id: "+idItem);
                intent = new Intent(this, HomeStudent.class);
                intent.putExtra("target", "home");
                startActivity(intent);
                break;
            case R.id.nav_profile_student:
                Log.d("MatieresOfUserActivity","R.id.nav_profile_student id: "+idItem);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new ProfileStudentFragment()).commit();
                break;
            case R.id.nav_teaching_student:
                Log.d("MatieresOfUserActivity","R.id.nav_teaching_student id: "+idItem);
                intent = new Intent(this, MatieresOfUserActivity.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //       new MatieresOfUserActivity()).commit();
                break;
            case R.id.nav_activityLog_student:
                Log.d("MatieresOfUserActivity","R.id.nav_activityLog_student id: "+idItem);

                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new QuizzFragment()).commit();
                break;
            case R.id.nav_settings_student:
                Log.d("MatieresOfUserActivity","R.id.nav_settings_student id: "+idItem);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new QuizzFragment()).commit();
                break;
            case R.id.nav_logout_student:
                Log.d("MatieresOfUserActivity","R.id.nav_logout_student id: "+idItem);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                //        new LogoutStudentFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                    Log.d("MatieresOfUserActivity", "printing first element of returned json: " + listMatieres.get(0).getId() + ": " +  listMatieres.get(0).getTitre());

                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}