package com.example.joseph.mooc.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joseph.mooc.BackgroundTasks.GetActivityLogofUserTask;
import com.example.joseph.mooc.Helper.ActiviteDbAdapter;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.ActivityDB;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by josep on 5/29/2019.
 */

public class ActivityLogFragment extends Fragment implements Callback {

    String user_id;
    ArrayList<ActivityDB> activities;
    RecyclerView recyclerActivities;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ActivityLogFragment", "OnCreate");
        this.user_id = getActivity().getSharedPreferences(GlobalProperties.login_sharedpreferences, MODE_PRIVATE).getString("id", null);
        Log.d("ActivityLogFragment", "userid from prefs: " + this.user_id);
        this.activities = new ArrayList<ActivityDB>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d("ActivityLogFragment", "OnCreateView");
        View view = inflater.inflate(R.layout.fragment_useractivities, container, false);
        //get user activities by calling the asyn task
        GetActivityLogofUserTask getActivityLogofUserTask = new GetActivityLogofUserTask(this);
        getActivityLogofUserTask.execute(this.user_id);
        return view;
    }

    public void initializeRecyclerView(){
        //init recyclerview with the matierearrayadapter
        this.recyclerActivities = getView().findViewById(R.id.recyclerActivities);
        ActiviteDbAdapter activiteArrayAdapter = new ActiviteDbAdapter(this.activities, getActivity(), R.layout.activitylog_listitem);
        recyclerActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerActivities.setItemAnimator(new DefaultItemAnimator());
        recyclerActivities.setHasFixedSize(true);
        //DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        //itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        //recyclerActivities.addItemDecoration(itemDecorator);


        recyclerActivities.setAdapter(activiteArrayAdapter);
    }
    @Override
    public void processData(String code, String data) {
        if (data != null && code.equals("getActivityLogTask")) {
            Log.d("getActivityLogTask", "Process data: code = " + code);
            Log.d("getActivityLogTask", "Process data: result = " + data);
            if (!data.equals("no_results") && !data.equals("error")) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i =  0; i < jsonArray.length(); i++) {
                        JSONObject act = jsonArray.getJSONObject(i);
                    Boolean toAdd = true;
                    for(int j = 0; j < this.activities.size(); j++){
                        if(this.activities.get(j).getId().equals(act.get("id"))){
                            toAdd = false;
                        }
                    }
                    if(toAdd) {
                        this.activities.add(new ActivityDB(act.getString("id"), act.getString("user_id"), act.getString("type"), act.getString("ref"), act.getString("date"), act.getString("time"), act.getString("activite_text")));
                        }
                    }
                    Log.d("getActivityLogTask", "Size of activity array: " + this.activities.size());
                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}