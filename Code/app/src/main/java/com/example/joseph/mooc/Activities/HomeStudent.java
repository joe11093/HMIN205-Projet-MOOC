package com.example.joseph.mooc.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class HomeStudent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private View headerLayout;
    private TextView txt_name;
    private TextView txt_email;
    private NavigationView nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        String header_name = getSharedPreferences(GlobalProperties.login_sharedpreferences, Context.MODE_PRIVATE).getString("firstname", null);
        String header_email = getSharedPreferences(GlobalProperties.login_sharedpreferences, Context.MODE_PRIVATE).getString("email", null);

        nav_view = findViewById(R.id.nav_view);
        headerLayout = nav_view.getHeaderView(0);

        txt_name = headerLayout.findViewById(R.id.nav_header_name);
        txt_email = headerLayout.findViewById(R.id.nav_header_email);

        header_name = header_name.replaceAll("^\"|\"$", "");
        header_email = header_email.replaceAll("^\"|\"$", "");

        txt_name.setText(header_name);
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

        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_home_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeStudentFragment()).commit();
                break;
            case R.id.nav_profile_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileStudentFragment()).commit();
                break;
            case R.id.nav_teaching_student:
                intent = new Intent(this, MatieresOfUserActivity.class);
                startActivity(intent);
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                 //       new MatieresOfUserActivity()).commit();
                break;
            case R.id.nav_activityLog_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new QuizzFragment()).commit();
                break;
            case R.id.nav_settings_student:
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
