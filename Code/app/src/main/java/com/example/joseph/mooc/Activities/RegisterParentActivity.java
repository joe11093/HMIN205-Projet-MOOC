package com.example.joseph.mooc.Activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.joseph.mooc.R;

import java.util.Calendar;

public class RegisterParentActivity extends AppCompatActivity {

    EditText firstname, lastname, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);

        firstname = findViewById(R.id.parentSignupFirstName);
        lastname = findViewById(R.id.parentSignupLasttName);
        dob = findViewById(R.id.parentSignupDOB);
    }


    public void signup(View view) {

    }
}
