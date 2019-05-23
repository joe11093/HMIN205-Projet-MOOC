package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.joseph.mooc.Helper.CheckConnectivity;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.R;

import org.w3c.dom.Text;

//this class finalizes the registration
//insert the students and parents into the database

public class FinishRegistrationActivity extends AppCompatActivity {

    Intent intent;
    Bundle bundle;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Finishregistration", "Finish registration Activity oncreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_registration);

        this.intent = getIntent();
        this.bundle = this.intent.getExtras();

        registerReceiver(
                new CheckConnectivity(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

        //test
        this.tv = findViewById(R.id.textView4);
        Parent parent = (Parent) bundle.get("parent");
        tv.setText("Registering: " + parent.getFirstname() + " " + parent.getLastname());
    }
}
