package com.example.joseph.mooc.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.joseph.mooc.R;

public class HomeParent extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_parent);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeParentFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home_parent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home_parent:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeParentFragment()).commit();
                break;
            case R.id.nav_profile_parent:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileParentFragment()).commit();
                break;
            case R.id.nav_children:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChildrenFragment()).commit();
                break;
            case R.id.nav_subscription:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SubscriptionFragment()).commit();
                break;
            case R.id.nav_logout_parent:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LogoutParentFragment()).commit();
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
