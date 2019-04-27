package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.joseph.mooc.R;

public class MainActivity extends AppCompatActivity {

    //CHECK SHARED PREFERENCES

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void login(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void signup(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
