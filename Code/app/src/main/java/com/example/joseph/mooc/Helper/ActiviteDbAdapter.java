package com.example.joseph.mooc.Helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joseph.mooc.Models.ActivityDB;
import com.example.joseph.mooc.R;
import java.util.ArrayList;

/**
 * Created by josep on 5/29/2019.
 */

public class ActiviteDbAdapter extends RecyclerView.Adapter<ActiviteDbAdapter.ViewHolder> {

    public ArrayList<ActivityDB> itemList;
    public Context context;
    private int listItemLayout;

    public ActiviteDbAdapter(ArrayList<ActivityDB> activities, Context context, int listItemLayout) {
        Log.d("ActiviteDbAdapter", "Constructor");
        this.itemList = activities;
        this.context = context;
        this.listItemLayout = listItemLayout;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public ActiviteDbAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ActiviteDbAdapter", "onCreateViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ActiviteDbAdapter.ViewHolder myViewHolder = new ActiviteDbAdapter.ViewHolder(view);
        view.setMinimumHeight(parent.getHeight() / 4);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ActiviteDbAdapter.ViewHolder holder, final int listPosition) {
        Log.d("ActiviteDbAdapter", "onBindViewHolder ");
        //what to do to an item in a the recycler when it appears on screen
        holder.activityItemMessage.setText(itemList.get(listPosition).getActivite_text());
        //String datetime = itemList.get(listPosition).getDate() + " at " + itemList.get(listPosition).getTime();
        //Log.d("ActiviteDbAdapter", datetime);
        holder.activityItemDate.setText(itemList.get(listPosition).getDate());
        holder.activityItemTime.setText(itemList.get(listPosition).getTime());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView activityItemMessage, activityItemDate, activityItemTime;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("ActiviteDbAdapter", "ViewHolder ");
            itemView.setOnClickListener(this);
            this.activityItemMessage = itemView.findViewById(R.id.activityItemMessage);
            this.activityItemDate = itemView.findViewById(R.id.activityItemDate);
            this.activityItemTime = itemView.findViewById(R.id.activityItemTime);
        }
        @Override
        public void onClick(View view) {

        }

    }


}
