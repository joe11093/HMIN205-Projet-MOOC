package com.example.joseph.mooc.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joseph.mooc.Activities.MatieresOfUserActivity;
import com.example.joseph.mooc.R;

public class HomeStudentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_student, container, false);
        getActivity().setTitle("HOME");
        return view;
    }

    public void teaching(View v){

        Intent intent = new Intent(getActivity(), MatieresOfUserActivity.class);
        startActivity(intent);

    }
}
