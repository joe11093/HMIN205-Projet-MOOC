package com.example.joseph.mooc.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class ProfileStudentFragment extends Fragment {

    SharedPreferences prefs;
    String email, firstname, lastname, city, country, annee, type;
    TextView emailTv, nameTv, addressTv, anneeTv, typeTv;
    Button gotolog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefs = getContext().getSharedPreferences(GlobalProperties.login_sharedpreferences, getContext().MODE_PRIVATE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_profile_student, container, false);
        getActivity().setTitle("PROFILE");

        this.emailTv = view.findViewById(R.id.student_profile_email);
        this.nameTv = view.findViewById(R.id.profile_name_student);
        this.typeTv = view.findViewById(R.id.profile_address_student);
        this.anneeTv = view.findViewById(R.id.profile_annee_student);
        this.addressTv = view.findViewById(R.id.address_parent);

        this.typeTv.setText(prefs.getString("type", null));
        this.emailTv.setText(prefs.getString("email", null));
        this.nameTv.setText(prefs.getString("firstname", null));
        this.typeTv.setText(prefs.getString("type", null));
        this.anneeTv.setText(prefs.getString("annee_scolaire", null));

        this.gotolog = view.findViewById(R.id.goToLog);
        this.gotolog.setText(getResources().getString(R.string.goToLog));
        this.gotolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ActivityLogFragment()).commit();
            }
        });

        return view;


    }
}
