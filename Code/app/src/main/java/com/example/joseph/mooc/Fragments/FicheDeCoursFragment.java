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
import com.example.joseph.mooc.Models.FicheDeCours;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

/**
 * Created by josep on 5/26/2019.
 */

public class FicheDeCoursFragment extends Fragment {

    MatiereActivity calling_activity;
    Matiere matiere;
    FicheDeCours ficheDeCours;
    TextView coursTitre;
    WebView coursContenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("FicheDeCoursFrag", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity)getActivity();
        this.matiere = this.calling_activity.matiere;
        this.ficheDeCours = this.calling_activity.ficheDeCours;
        //Log.d("FicheDeCoursFrag", "FicheID : " + this.ficheDeCours.getId() + " Titree : " + this.ficheDeCours.getTitre());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fichedecours, container, false);
        this.coursTitre = view.findViewById(R.id.ficheDeCoursTitre);
        this.coursContenu = view.findViewById(R.id.fichDeCoursContenu);

        this.coursContenu.loadData("<h1>" + this.ficheDeCours.getTitre()+"</h2>" + this.ficheDeCours.getContenu(), "text/html; charset=utf-8", "UTF-8"   );
        return view;
    }
}
