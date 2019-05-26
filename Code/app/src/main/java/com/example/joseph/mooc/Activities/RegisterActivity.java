package com.example.joseph.mooc.Activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.joseph.mooc.R;

/*
This activity handles the user registration process
 */
public class RegisterActivity extends AppCompatActivity {

    private RadioGroup radiogroup;
    private RadioButton signup, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void redirectRegistration(View v){
        this.radiogroup = (RadioGroup) findViewById(R.id.RGUserType);
        this.signup = (RadioButton) findViewById(R.id.btnParent);
        this.login = (RadioButton) findViewById(R.id.btnStudent);

        int idchecked = radiogroup.getCheckedRadioButtonId();

        if(idchecked == R.id.btnParent){
            Intent intent = new Intent(this, RegisterParentActivity.class);
            startActivity(intent);
        }
        else
            if(idchecked == R.id.btnStudent){
                Intent intent = new Intent(this, RegisterStudentActivity.class);
                startActivity(intent);
            }
    }
}
