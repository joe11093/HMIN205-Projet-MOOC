package com.example.joseph.mooc.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.Models.QCM;
import com.example.joseph.mooc.R;

/**
 * Created by josep on 5/26/2019.
 */

public class QCMFragment extends Fragment {
    MatiereActivity calling_activity;
    Matiere matiere;
    QCM qcm;
    TextView QCMTitre;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("FicheDeCoursFrag", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity)getActivity();
        this.matiere = this.calling_activity.matiere;
        this.qcm = this.calling_activity.qcm;
        //Log.d("FicheDeCoursFrag", "FicheID : " + this.ficheDeCours.getId() + " Titree : " + this.ficheDeCours.getTitre());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qcm, container, false);
        this.QCMTitre = view.findViewById(R.id.qcmTitre);
        QCMTitre.setText(this.qcm.getTitre());
        return view;
    }
}
