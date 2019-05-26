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
import com.example.joseph.mooc.BackgroundTasks.QuizListAsyncTask;
import com.example.joseph.mooc.Helper.QCMArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.Models.QCM;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class QuizListFragment extends Fragment implements Callback{
    MatiereActivity calling_activity;
    Matiere matiere;
    RecyclerView qcmRecycler;
    ArrayList<QCM> listQCM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("QCMstFragment", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity) getActivity();
        this.matiere = this.calling_activity.matiere;

        this.listQCM = new ArrayList<QCM>();
        Log.d("QCMstFragment", "MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());
        //execute asyn task
        Log.d("QCMstFragment", "OnCreate: Length of listQCM" + this.listQCM.size());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qcmlist, container, false);
        this.qcmRecycler = view.findViewById(R.id.recyclerQuiz);
        Log.d("QCMstFragment", "OnCreateView: Length of listQCMDeCours" + this.listQCM.size());
        QuizListAsyncTask task = new QuizListAsyncTask(this);
        task.execute(this.matiere);
        return view;
    }

    public void initializeRecyclerView(){
        //init recyclerview with the matierearrayadapter
        this.qcmRecycler = getView().findViewById(R.id.recyclerQuiz);
        QCMArrayAdapter qcmArrayAdapter = new QCMArrayAdapter(calling_activity, R.layout.qcm_list_item, this.listQCM);
        qcmRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        qcmRecycler.setItemAnimator(new DefaultItemAnimator());
        qcmRecycler.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        qcmRecycler.addItemDecoration(itemDecorator);

        qcmRecycler.setAdapter(qcmArrayAdapter);
    }

    @Override
    public void processData(String code, String data) {
        if(data != null && code.equals("QuizListAsyncTask")){
            //there's data coming from the task
            Log.d("QCMstFragment", "Process data: code = " + code);
            Log.d("QCMstFragment", "Process data: result = " + data);
            if(!data.equals("no_results") && !data.equals("error")){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i =  0; i < jsonArray.length(); i++){
                        JSONObject qcm = jsonArray.getJSONObject(i);
                        Boolean toAdd = true;
                        for(int j = 0; j < this.listQCM.size(); j++){
                            if(this.listQCM.get(j).getId().equals(qcm.get("id"))){
                                toAdd = false;
                            }
                        }
                        if(toAdd)
                            this.listQCM.add(new QCM(qcm.getString("id"), qcm.getString("titre"), qcm.getString("type_ref"), qcm.getString("ref"), qcm.getString("matiere_id"), qcm.getString("annee_id")));
                    }
                    Log.d("QCMstFragment", "printing first element ONE returned cours: " + listQCM.get(0).getId() + ": " +  listQCM.get(0).getTitre());

                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}