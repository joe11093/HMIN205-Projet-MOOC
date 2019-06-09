package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
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
import com.example.joseph.mooc.BackgroundTasks.GetAllTask;
import com.example.joseph.mooc.BackgroundTasks.SignupBackgroundTask;
import com.example.joseph.mooc.Helper.AnneeScolaireArrayAdapter;
import com.example.joseph.mooc.Helper.CheckConnectivity;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.AnneeScolaire;
import com.example.joseph.mooc.Models.Parent;
import com.example.joseph.mooc.Models.Student;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterStudentActivity extends AppCompatActivity  implements Callback{
    EditText fname, lname, dob, email, pass, city, country;
    String fnamestr, lnamestr, dobstr, emailstr, passstr,citystr,countrystr;
    Spinner anneeSpnr;
    TextView testTextView, signupMessage;
    Parent parent = null;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StudentRegistration", "In oncreate of register student activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

       /* registerReceiver(
                new CheckConnectivity(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));*/

        this.fname = findViewById(R.id.studentSignupFirstName);
        this.lname = findViewById(R.id.studentSignupLasttName);
        this.dob = findViewById(R.id.studentSignupDOB);
        this.email = findViewById(R.id.studentSignupEmail);
        this.pass = findViewById(R.id.studentSignupPassword);
        this.city = findViewById(R.id.studentSignupCity);
        this.country = findViewById(R.id.studentSignupCountry);
        this.signupMessage = findViewById(R.id.studentregMessagetx);

        this.testTextView = findViewById(R.id.studentregTesttx);

        this.anneeSpnr = findViewById(R.id.studentSignupAnneeScolaireSpinner);

        //get parent from the intent
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Parent parent;

        if(bundle != null && bundle.containsKey("parent")){
            parent = (Parent) bundle.get("parent");
            Log.d("StudentRegistration", "parent email address: " + parent.getEmailaddress());
        }
        //fill spinner from database
        this.fillSpinner("annee_scolaire");


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

        //create student object and check if student is already in the db by calling the asynctask
        student = student = new Student(fnamestr, lnamestr, dobstr, emailstr, passstr, citystr, countrystr, chosen_anne, parent);

        if(parent == null){
            //case where an individual student is signing up, not a student being signed up by his parent
            Log.d("StudentRegistration", "signup function: single student");
            //student = new Student(fnamestr, lnamestr, dobstr, emailstr, passstr, citystr, countrystr, chosen_anne, null);
            //CheckObjectExistInDbAsyncTask existtask = new CheckObjectExistInDbAsyncTask(this);
            //existtask.execute(student);
            SignupBackgroundTask signupBackgroundTask = new SignupBackgroundTask(this);
            signupBackgroundTask.execute(student);
            Log.d("StudentRegistration", "single student: after exist task");
        }
        else{
            //case where a parent is signing up a student
            //student = new Student(fnamestr, lnamestr, dobstr, emailstr, passstr, citystr, countrystr, chosen_anne, parent);
            //check if student is in DB before.
            //CheckObjectExistInDbAsyncTask existtask = new CheckObjectExistInDbAsyncTask(this);
            //existtask.execute(student);
            //Log.d("afterexecute", "afterexecute");
        }

    }

    public void fillSpinner(String spinner_type){
        GetAllTask getAllTask = new GetAllTask(this);
        getAllTask.execute(spinner_type);
    }

    public void setSpinnerAdapter(Spinner s, String data){
        if(!data.equals("error") && !data.equals("no_results")) {
            try {
                JSONArray jsonArray = new JSONArray(data);
                AnneeScolaire[] anneeScolairesArray = new AnneeScolaire[jsonArray.length()];

                for(int i =  0; i < jsonArray.length(); i++){
                    JSONObject annee = jsonArray.getJSONObject(i);
                    anneeScolairesArray[i] = new AnneeScolaire(annee.getInt("id"), annee.getString("annee_scolaire"));
                }
                Log.d("StudentRegistration", "getalltask: printing first element of returned json: " + anneeScolairesArray[0].getId() + ": " + anneeScolairesArray[0].getAnneescolaire());
                //set the adapter
                AnneeScolaireArrayAdapter adapter = new AnneeScolaireArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, anneeScolairesArray);
                anneeSpnr.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void processData(String code, String data) {
        Log.d("StudentRegistration", "On Process");
        Log.d("StudentRegistration", "data: " + data);
        Log.d("StudentRegistration", "code: " + code);

        //useless?
        if(code.equals("existstask")){
            Log.d("StudentRegistration", "in if(code = existstask)");
            if (data.equals("true")) {
                Log.d("StudentRegistration", "exists = true");
                //student already exists in db
                this.testTextView.setText("Your email is already in use");

            }
            else if(data.equals("false")){
                //student doesn't exist in db
                Log.d("StudentRegistration", "exists = false");
                if((this.parent == null)){
                    //signing up a student with no parent
                    Log.d("StudentRegistration", "process data: signing up a student with no parent");
                    //execute signup task
                    SignupBackgroundTask signupBackgroundTask = new SignupBackgroundTask(this);
                    signupBackgroundTask.execute(student);

                    Intent intent = new Intent(this, LaunchActivity.class);
                    startActivity(intent);
                }
                else{
                    //student with parent
                    Log.d("StudentRegistration", "process data: signing up a student with parent");
                }

            }
        }

        //if result is from signup task
        else if(code.equals("signuptask")){
            Log.d("StudentRegistration", "process data: Signup task");
            Log.d("StudentRegistration", "code= " + code + " data = " + data);
            if(data.equals("true")){
                //sign up is successfull
                this.signupMessage.setTextColor(Color.GREEN);
                this.signupMessage.setText(R.string.signupSuccessMessage);
                Intent i = new Intent(this, LoginActivity.class);
                i.putExtra("message", getResources().getString(R.string.afterSignupLoginMessage));
                startActivity(i);
            }

            else{
                //signup wasn't successfull
                this.signupMessage.setTextColor(Color.RED);
                this.signupMessage.setText(R.string.signupFailMessage);
            }
        }
        //if we're getting all annee scolaire
        else if(code.equals("getall-annee_scolaire")){
            Log.d("StudentRegistration", "process data: getalltask");
            Log.d("StudentRegistration", "data: \n" + data);

            //fill the spinner with the received data
            this.setSpinnerAdapter(this.anneeSpnr, data);

        }
        Log.d("StudentRegistration", "process data: finishing process datass");

    }


}