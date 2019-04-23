package com.example.joseph.mooc.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.joseph.mooc.Helper.AppSettings;
import com.example.joseph.mooc.R;

public class RegisterStudentActivity extends AppCompatActivity {

    Spinner classspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        this.classspinner = findViewById(R.id.studentSignupClassSpinner);
        ArrayAdapter<String> classspinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        AppSettings.classes);
        classspinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        classspinner.setAdapter(classspinnerArrayAdapter);
    }


    public void signup(View view) {

    }
}
