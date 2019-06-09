package com.example.joseph.mooc.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mooc.BackgroundTasks.CheckObjectExistInDbAsyncTask;
import com.example.joseph.mooc.Helper.CheckConnectivity;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegisterParentActivity extends AppCompatActivity implements Callback {

    EditText firstname, lastname, dob, emailaddress, password, city, country;
    Spinner numberofchildrenSpinner;
    TextView testTxt;
    Parent parent;
    ArrayList<Student> students = new ArrayList<Student>();
    Intent intentStudentRegistration, intentFinishRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_parent);

        registerReceiver(
                new CheckConnectivity(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

        //set up intents
        intentStudentRegistration = new Intent(this, RegisterStudentActivity.class);
        intentFinishRegistration = new Intent(this, FinishRegistrationActivity.class);

        //getting fields
        firstname = findViewById(R.id.parentSignupFirstName);
        lastname = findViewById(R.id.parentSignupLasttName);
        dob = findViewById(R.id.parentSignupDOB);
        emailaddress = findViewById(R.id.parentSignupEmail);
        password = findViewById(R.id.parentSignupPassword);
        city = findViewById(R.id.parentSignupCity);
        country = findViewById(R.id.parentSignupCountry);

        testTxt = findViewById(R.id.testtx);
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
        parent = new Parent(fname, lname, datebirth, email, pass, citystring, countrystring);

        //check to see if parent's email exists in DB
        CheckObjectExistInDbAsyncTask existtask = new CheckObjectExistInDbAsyncTask(this);
        existtask.execute(parent);
        Log.d("afterexecute", "afterexecute");
        //if yes, add message in the sign up screen

        //if no then the parent is valid
        //if number of children == 0
            //insert Parent into database
            //Take parent to sucess screen informing him that an email is gonna be sent
        //if number of children > 0


    }

    @Override
    public void processData(String code, String data) {
        //Toast.makeText(this, "Result: " + data, Toast.LENGTH_LONG).show();
        //Log.d("datafromasync", data);
        if(data.equals("true")){
            this.testTxt.setText("Your email is already in use");
        }
        else{
            this.testTxt.setText("You can sign up");
            int nbchildren = (int) this.numberofchildrenSpinner.getSelectedItem();
            Log.d("numchildren", ""+nbchildren);


            if(nbchildren > 0){
                Log.d("nbchildrenGT0", ""+nbchildren);
                intentStudentRegistration.putExtra("parent", parent);

                //delete this part once it works
                Parent parentfromintent = (Parent)intentStudentRegistration.getExtras().get("parent");
                Log.d("studentregputextra", parentfromintent.getEmailaddress());
                //loop over number of children
                //open children sign up activity
                //get children and add it to intent
                //once loop is over, add the parent and every child to the database
                //and go to Confirmation activity

                //for(int i = 0; i < nbchildren; i++){
                    //Log.d("startactivityloop", ""+i);
                    startActivityForResult(intentStudentRegistration, 1);
                //}
                //add the students arraylist to the finish registration intent
                //this.intentFinishRegistration.putExtra("parent", parent);
                //this.intentFinishRegistration.putExtra("studentList", students);
                //start finish registration activity
                //startActivity(intentFinishRegistration);

            }
            else{
                Log.d("else", "else, number children <= 0");
                //finish registration process
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnData) {

        Toast.makeText(this, "child info was registered", Toast.LENGTH_LONG).show();
        Bundle bundle = returnData.getExtras();
        Student student = (Student) bundle.get("student");
        Log.d("registeredstudent", student.getFirstname());
        if(resultCode == RESULT_OK){
            students.add((Student)bundle.get("student"));
        }
    }
}
