package com.example.joseph.mooc.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.BackgroundTasks.FicheDeCoursListMatiereAnneeAsyncTask;
import com.example.joseph.mooc.Helper.FicheDeCoursArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.FicheDeCours;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josep on 5/25/2019.
 */

public class FicheDeCoursListFragment extends Fragment implements Callback {

    MatiereActivity calling_activity;
    Matiere matiere;
    RecyclerView ficheDeCoursRecycler;
    ArrayList<FicheDeCours> listFicheDeCours;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("CoursListFragment", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity)getActivity();
        this.matiere = this.calling_activity.matiere;
        this.listFicheDeCours = new ArrayList<FicheDeCours>();
        Log.d("CoursListFragment", "MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());
        //execute asyn task
        Log.d("CoursListFragment", "OnCreate: Length of listFicheDeCours" + this.listFicheDeCours.size());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courslist, container, false);
        this.ficheDeCoursRecycler = view.findViewById(R.id.recyclerCours);
        Log.d("CoursListFragment", "OnCreateView: Length of listFicheDeCours" + this.listFicheDeCours.size());
        FicheDeCoursListMatiereAnneeAsyncTask task = new FicheDeCoursListMatiereAnneeAsyncTask(this);
            task.execute(this.matiere);


        return view;
    }



    public void initializeRecyclerView(){
        //init recyclerview with the matierearrayadapter
        this.ficheDeCoursRecycler = getView().findViewById(R.id.recyclerCours);
        FicheDeCoursArrayAdapter matiereArrayAdapter = new FicheDeCoursArrayAdapter(calling_activity, R.layout.cours_list_item, this.listFicheDeCours);
        ficheDeCoursRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ficheDeCoursRecycler.setItemAnimator(new DefaultItemAnimator());
        ficheDeCoursRecycler.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        ficheDeCoursRecycler.addItemDecoration(itemDecorator);


        ficheDeCoursRecycler.setAdapter(matiereArrayAdapter);
    }
    @Override
    public void processData(String code, String data) {
        if(data != null && code.equals("CoursListMatiereTask")){
            //there's data coming from the task
            Log.d("CoursListFragment", "Process data: code = " + code);
            Log.d("CoursListFragment", "Process data: result = " + data);
            if(!data.equals("no_results") && !data.equals("error")){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i =  0; i < jsonArray.length(); i++){
                        JSONObject cours = jsonArray.getJSONObject(i);
                        Boolean toAdd = true;
                        for(int j = 0; j < this.listFicheDeCours.size(); j++){
                            if(this.listFicheDeCours.get(j).getId().equals(cours.get("id"))){
                                toAdd = false;
                            }
                        }
                        if(toAdd)
                            this.listFicheDeCours.add(new FicheDeCours(cours.getString("id"), cours.getString("chapitre"), cours.getString("titre"),  cours.getString("contenu"), cours.getString("matiere_id"),  cours.getString("annee_id")));
                    }
                    Log.d("CoursListFragment", "printing first element ONE returned cours: " + listFicheDeCours.get(0).getId() + ": " +  listFicheDeCours.get(0).getTitre());

                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
