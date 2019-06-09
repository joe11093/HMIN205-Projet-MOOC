package com.example.joseph.mooc.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joseph.mooc.Activities.HomeStudent;
import com.example.joseph.mooc.Activities.MainActivity;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.R;

public class LogoutStudentFragment extends Fragment {
    SharedPreferences prefs;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        prefs = getActivity().getSharedPreferences(GlobalProperties.login_sharedpreferences, Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_logout_student, container, false);
        getActivity().setTitle("");
        openDialog();
        return view;
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Log Out")
                .setMessage("Do you really want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout(view);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), HomeStudent.class);
                        startActivity(intent);
                    }
                });;
        builder.show();
    }

    public void logout(View view) {
        SharedPreferences.Editor editor =prefs.edit();
        Log.d("LOGOUT_Student","HELLO");
        editor.remove("id");
        editor.remove("firstname");
        editor.remove("email");
        editor.remove("type");
        editor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
