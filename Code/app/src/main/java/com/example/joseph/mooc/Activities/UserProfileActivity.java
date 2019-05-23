package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mooc.Helper.CheckConnectivity;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class UserProfileActivity extends AppCompatActivity {
    String userId, firstname;
    TextView topOfPageText;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        registerReceiver(
                new CheckConnectivity(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

        prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        String loggedInId = prefs.getString("id", null);
        String loggedInFirstName = prefs.getString("firstname", null);
        this.userId = loggedInId;
        this.firstname = loggedInFirstName;

        this.topOfPageText = findViewById(R.id.userProfileTopOfPage);
        topOfPageText.setText("Hello " + loggedInId + ", this is your profile page");


    }

    public void logout(View view) {
        SharedPreferences.Editor editor =prefs.edit();

        editor.remove("id");
        editor.remove("firstname");
        editor.remove("email");
        editor.remove("type");
        editor.apply();

        Toast.makeText(this, R.string.logoutMessage, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LaunchActivity.class);
        startActivity(intent);
    }
}
