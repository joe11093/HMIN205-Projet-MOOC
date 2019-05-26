package com.example.joseph.mooc.Helper;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.Models.Video;
import com.example.joseph.mooc.R;

import java.util.ArrayList;

/**
 * Created by josep on 5/25/2019.
 */

public class VideoDeCoursArrayAdapter extends RecyclerView.Adapter<VideoDeCoursArrayAdapter.ViewHolder> {
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Video> itemList;
    // Constructor of the class
    public VideoDeCoursArrayAdapter(int layoutId, ArrayList<Video> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public VideoDeCoursArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        VideoDeCoursArrayAdapter.ViewHolder myViewHolder = new VideoDeCoursArrayAdapter.ViewHolder(view);
        view.setMinimumHeight(parent.getHeight()/4);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final VideoDeCoursArrayAdapter.ViewHolder holder, final int listPosition) {
    //what to do to an item in a the recycler when it appears on screen
        holder.videoTitre.setText("Ch." + itemList.get(listPosition).getChapitre() + ": " + itemList.get(listPosition).getTitre());
        holder.videoId.setText(itemList.get(listPosition).getId());
        holder.videoChapitre.setText(itemList.get(listPosition).getChapitre());
        holder.videoURL.setText(itemList.get(listPosition).getUrl());
        holder.videoMatiereId.setText(itemList.get(listPosition).getMatiere_id());
        holder.videoAnneeId.setText(itemList.get(listPosition).getAnnee_id());

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView videoTitre, videoId, videoChapitre, videoURL, videoMatiereId, videoAnneeId;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            videoTitre = itemView.findViewById(R.id.videoTitre);
            videoId = itemView.findViewById(R.id.videoId);
            videoChapitre = itemView.findViewById(R.id.videoChapitre);
            videoURL = itemView.findViewById(R.id.videoURL);
            videoMatiereId = itemView.findViewById(R.id.videoMatiereId);
            videoAnneeId = itemView.findViewById(R.id.videoAnneeId);

        }
        @Override
        public void onClick(View view) {
            Log.d("videoarrayadapter", "onClick " + getLayoutPosition() + " " + videoTitre.getText());
            Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL.getText().toString()));

            Log.d("videoarrayadapter", "URL of the video: " + this.videoURL.getText().toString());
            view.getContext().startActivity(intent);

        }
    }
}
