package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.joseph.mooc.BackgroundTasks.BackgroundTask;
import com.example.joseph.mooc.BackgroundTasks.LoginAsyncClass;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.*;
import com.example.joseph.mooc.R;
import com.example.joseph.mooc.Models.Login;

import org.json.JSONObject;

import java.util.Map;

public class LoginActivity extends AppCompatActivity implements Callback {
    //get username and password from user
    //check them in the DB
    //if correct, log in user by adding him to the shared preferences


    EditText emailEditText, passwordEditText;
    TextView loginMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "started the login activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get the edit texts
        this.emailEditText = findViewById(R.id.loginEmail);
        this.passwordEditText = findViewById(R.id.loginPassword);
        this.loginMessage = findViewById(R.id.loginMessage);

        Bundle bundle = getIntent().getExtras();
        Log.d("LoginActivity", bundle.get("message").toString());
        if(bundle != null && bundle.containsKey("message")){
            this.loginMessage.setText((String) bundle.get("message"));
        }
    }

    public void login(View v){
        Log.d("LoginActivity", "Login Function");
        Toast.makeText(this, "Log in function", Toast.LENGTH_SHORT).show();
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();
        Login login = new Login(email, password);

        //execute the login async task
        Log.d("LoginActivity", "Starting the login async task");
        LoginAsyncClass loginAsyncClass = new LoginAsyncClass(this);
        loginAsyncClass.execute(login);
    }

    @Override
    public void processData(String code, String data) {
        Log.d("LoginActivity", "processdata function");
        Log.d("LoginActivity", "Code: " + code);
        Log.d("LoginActivity", "Data: " + data);
        if(code == "logintask") {
            Log.d("LoginActivity", "Code: "+code);
            Log.d("LoginActivity", "Process data for loginTask");
            if (data.equals(null)) {
                Log.d("LoginActivity", "Process data for login. Data is null.");
                Toast.makeText(this, "NULL", Toast.LENGTH_LONG).show();
            } else {
                if (data.equals("error")) {
                    Toast.makeText(this, "NULL", Toast.LENGTH_LONG).show();
                    Log.d("LoginActivity", "Process data for login. Data is error.");
                } else {
                    //no error
                    Toast.makeText(this, data, Toast.LENGTH_LONG).show();
                    Log.d("LoginActivity", "login result: " + data);

                    //add info to shared preferences
                    SharedPreferences.Editor editor = getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE).edit();
                    data = data.replace("{", "");
                    data = data.replace("}", "");
                    String[] array = data.split(",");
                    String id = array[0].split(":")[1];
                    String firstname = array[1].split(":")[1];
                    String email = array[2].split(":")[1];
                    String type = array[3].split(":")[1];
                    Log.d("splitarray", id);
                    Log.d("splitarray", firstname);
                    Log.d("splitarray", email);
                    Log.d("splitarray", type);

                    editor.putString("id", id);
                    editor.putString("firstname", firstname);
                    editor.putString("email", email);
                    editor.putString("type", type);
                    editor.apply();
                    Log.d("LoginActivity", "Process data for login. Added to shared preferences.");
                    Intent intent = new Intent(this, UserProfileActivity.class);
                    startActivity(intent);

                }
            }
        }
        else{
            //code different than login
            Log.d("LoginActivity", "Process data for funcction other than login.");
        }
    }
}
