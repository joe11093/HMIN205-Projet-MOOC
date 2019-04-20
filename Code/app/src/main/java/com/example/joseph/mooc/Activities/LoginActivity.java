package com.example.joseph.mooc.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.joseph.mooc.BackgroundTasks.BackgroundTask;
import com.example.joseph.mooc.R;

public class LoginActivity extends AppCompatActivity {
    EditText e_id;
    EditText e_firstname;
    String e_id_str, e_firstname_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e_id = (EditText) findViewById(R.id.id);
        e_firstname = (EditText) findViewById(R.id.firstname);
    }

    public void login(View v){
        Toast.makeText(this, "Log in function", Toast.LENGTH_SHORT).show();
        /*
        e_id_str = e_id.getText().toString();
        e_firstname_str  = e_firstname.getText().toString();

        String method = "registerUser";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, e_id_str, e_firstname_str);
        finish();
        */
    }
}
