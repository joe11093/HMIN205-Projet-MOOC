package com.example.joseph.mooc.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class HomeStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private SharedPreferences prefs;
    private TextView txt_name;
    private TextView txt_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        prefs = this.getSharedPreferences(GlobalProperties.login_sharedpreferences, Context.MODE_PRIVATE);
        String header_name = prefs.getString("name", null);
        String header_email = prefs.getString("email", null);
        txt_name = findViewById(R.id.nav_header_name);
        txt_name.setText(header_name);
        txt_email = findViewById(R.id.nav_header_email);
        txt_email.setText(header_email);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_student);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeStudentFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home_parent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeStudentFragment()).commit();
                break;
            case R.id.nav_profile_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileStudentFragment()).commit();
                break;
            case R.id.nav_courses:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CoursesFragment()).commit();
                break;
            case R.id.nav_quizz:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new QuizzFragment()).commit();
                break;
            case R.id.nav_logout_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogoutStudentFragment()).commit();
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
}
