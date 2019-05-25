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
import com.example.joseph.mooc.BackgroundTasks.VideoListOfMatiereAnneeAsyncTask;
import com.example.joseph.mooc.Helper.MatiereArrayAdapter;
import com.example.joseph.mooc.Helper.VideoDeCoursArrayAdapter;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.Models.Video;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by josep on 5/25/2019.
 */

public class VideoListFragment extends Fragment implements Callback{

    MatiereActivity calling_activity;
    Matiere matiere;
    RecyclerView videoRecycler;
    ArrayList<Video> listVideo = new ArrayList<Video>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("VideoListFragment", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity)getActivity();
        this.matiere = this.calling_activity.matiere;
        Log.d("VideoListFragment", "ONCREATE:  Calling AsyncTask");
        Log.d("VideoListFragment", "MatiereID : " + this.matiere.getId() + " AnneeId : " + this.matiere.getAnnee_id());
        //execute asyn task

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_videolist, container, false);
        this.videoRecycler = view.findViewById(R.id.recyclerVideos);
        VideoListOfMatiereAnneeAsyncTask task = new VideoListOfMatiereAnneeAsyncTask(this);
        task.execute(this.matiere);
        return view;

    }

    public void initializeRecyclerView(){
        //init recyclerview with the matierearrayadapter
        this.videoRecycler = getView().findViewById(R.id.recyclerVideos);
        VideoDeCoursArrayAdapter matiereArrayAdapter = new VideoDeCoursArrayAdapter(R.layout.video_list_item, this.listVideo);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        videoRecycler.setItemAnimator(new DefaultItemAnimator());
        videoRecycler.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        videoRecycler.addItemDecoration(itemDecorator);


        videoRecycler.setAdapter(matiereArrayAdapter);


    }
    @Override
    public void processData(String code, String data) {
        if(data != null && code.equals("VideoListMatiereTask")){
            //there's data coming from the task
            Log.d("VideoListFragment", "Process data: code = " + code);
            Log.d("VideoListFragment", "Process data: result = " + data);
            if(!data.equals("no_results") && !data.equals("error")){
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i =  0; i < jsonArray.length(); i++){
                        JSONObject video = jsonArray.getJSONObject(i);
                        this.listVideo.add(new Video(video.getString("id"), video.getString("chapitre"), video.getString("titre"),  video.getString("url"), video.getString("matiere_id"),  video.getString("annee_id")));
                    }
                    Log.d("VideoListFragment", "printing first element ONE returned Video: " + listVideo.get(0).getId() + ": " +  listVideo.get(0).getTitre());

                    initializeRecyclerView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
