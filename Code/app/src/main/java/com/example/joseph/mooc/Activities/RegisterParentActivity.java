package com.example.joseph.mooc.Activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.joseph.mooc.BackgroundTasks.CheckObjectExistInDbAsyncTask;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterParentActivity extends AppCompatActivity {

    EditText firstname, lastname, dob, emailaddress, password, city, country;
    Spinner numberofchildrenSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);

        firstname = findViewById(R.id.parentSignupFirstName);
        lastname = findViewById(R.id.parentSignupLasttName);
        dob = findViewById(R.id.parentSignupDOB);
        emailaddress = findViewById(R.id.parentSignupEmail);
        password = findViewById(R.id.parentSignupPassword);
        city = findViewById(R.id.parentSignupCity);
        country = findViewById(R.id.parentSignupCountry);

        //fill spinner with possible number of children
        numberofchildrenSpinner = (Spinner) findViewById(R.id.childrenNumberSpinner);
        Integer[] numbers = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, numbers);
        numberofchildrenSpinner.setAdapter(adapter);
    }


    public void signup(View view) {
        //gettings strings of all input values
        String fname = firstname.getText().toString();
        String lname = lastname.getText().toString();
        String email = emailaddress.getText().toString();
        String datebirth = dob.getText().toString();
        String pass = password.getText().toString();
        String citystring = city.getText().toString();
        String countrystring = country.getText().toString();


        //creating parent object
        Parent p = new Parent(fname, lname, datebirth, email, pass, citystring, countrystring);

        //check to see if parent's email exists in DB
        CheckObjectExistInDbAsyncTask existtask = new CheckObjectExistInDbAsyncTask(this);
        existtask.execute(p);
        Log.d("afterexecute", "afterexecute");
        //if yes, add message in the sign up screen

        //if no then the parent is valid
        //if number of children == 0
            //insert Parent into database
            //Take parent to sucess screen informing him that an email is gonna be sent
        //if number of children > 0
        //loop over number of children
            //open children sign up activity
            //get children and add it to intent
        //once loop is over, add the parent and every child to the database
        //and go to Confirmation activity

    }
}
