package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

//CHECK SHARED PREFERENCES
    /*
    SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    String restoredText = prefs.getString("text", null);
    if (restoredText != null) {
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        int idName = prefs.getInt("idName", 0); //0 is the default value.
    }
    */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //check SharedPreferences to see if user is logged in
        SharedPreferences prefs = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE);
        String loggedInId = prefs.getString("id", null);

        if(loggedInId != null){
            Log.d("loggedinId", loggedInId);
            Toast.makeText(this, "A USER IS LOGGED IN", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "NO ONE IS LOGGED IN", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
