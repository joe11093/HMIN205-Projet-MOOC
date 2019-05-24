package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.joseph.mooc.BackgroundTasks.CheckObjectExistInDbAsyncTask;
import com.example.joseph.mooc.Helper.AnneeScolaireArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.AnneeScolaire;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.R;

public class RegisterStudentActivity extends AppCompatActivity  implements Callback{
    EditText fname, lname, dob, email, pass, city, country;
    String fnamestr, lnamestr, dobstr, emailstr, passstr,citystr,countrystr;
    Spinner anneeSpnr;
    TextView testTextView;
    Parent parent;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("studentregisteroncreate", "In oncreate of register student activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        this.fname = findViewById(R.id.studentSignupFirstName);
        this.lname = findViewById(R.id.studentSignupLasttName);
        this.dob = findViewById(R.id.studentSignupDOB);
        this.email = findViewById(R.id.studentSignupEmail);
        this.pass = findViewById(R.id.studentSignupPassword);
        this.city = findViewById(R.id.studentSignupCity);
        this.country = findViewById(R.id.studentSignupCountry);

        this.testTextView = findViewById(R.id.studentregTesttx);

        this.anneeSpnr = findViewById(R.id.studentSignupAnneeScolaireSpinner);

        AnneeScolaire[] annee_list = new AnneeScolaire[]{new AnneeScolaire(4, "4eme"), new AnneeScolaire(5, "5eme")};

        AnneeScolaireArrayAdapter adapter = new AnneeScolaireArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, annee_list);
        anneeSpnr.setAdapter(adapter);

        //get parent from the intent
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Parent parent = (Parent) bundle.get("parent");
        Log.d("parentinstudentactivity", parent.getEmailaddress());
    }
    public void signup(View view) {
        fnamestr = fname.getText().toString();
        lnamestr = lname.getText().toString();
        dobstr = dob.getText().toString();
        emailstr = email.getText().toString();
        passstr = pass.getText().toString();
        citystr = city.getText().toString();
        countrystr = country.getText().toString();
        AnneeScolaire chosen_anne = (AnneeScolaire) anneeSpnr.getSelectedItem();


        if(parent == null){
            //case where an individual student is signing up, not a student being signed up by his parent
        }
        else{
            //case where a parent is signing up a student
            student = new Student(fnamestr, lnamestr, dobstr, emailstr, passstr, citystr, countrystr, chosen_anne, parent);
            //check if student exists in DB before.
            CheckObjectExistInDbAsyncTask existtask = new CheckObjectExistInDbAsyncTask(this);
            existtask.execute(student);
            Log.d("afterexecute", "afterexecute");
        }


    }

    public void processData(String data) {
        Log.d("StudentRegistration", data);
        if(data.equals("true")){
            this.testTextView.setText("Your email is already in use");

        }
        else{
            this.testTextView.setText("You can sign up");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("student", student);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public void processData(String code, String data) {

    }
}
