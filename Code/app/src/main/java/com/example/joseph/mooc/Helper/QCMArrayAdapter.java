package com.example.joseph.mooc.Helper;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.BackgroundTasks.AddActivityToDBTask;
import com.example.joseph.mooc.Fragments.QCMFragment;
import com.example.joseph.mooc.Models.ActivityDB;
import com.example.joseph.mooc.Models.QCM;
import com.example.joseph.mooc.R;

import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class QCMArrayAdapter extends RecyclerView.Adapter<QCMArrayAdapter.ViewHolder> {
    private int listItemLayout;
    private ArrayList<QCM> itemList;
    public MatiereActivity matiereActivity;

    // Constructor of the class
    public QCMArrayAdapter(MatiereActivity matiereActivity, int layoutId, ArrayList<QCM> itemList) {
        Log.d("qcmarrayadapter", "Constructor ");
        listItemLayout = layoutId;
        this.itemList = itemList;
        this.matiereActivity = matiereActivity;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public QCMArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("qcmarrayadapter", "onCreateViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        QCMArrayAdapter.ViewHolder myViewHolder = new QCMArrayAdapter.ViewHolder(view);
        view.setMinimumHeight(parent.getHeight()/4);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final QCMArrayAdapter.ViewHolder holder, final int listPosition) {
        Log.d("qcmarrayadapter", "onBindViewHolder ");
        //what to do to an item in a the recycler when it appears on screen
        holder.qcmId.setText(itemList.get(listPosition).getId());
        holder.qcmTitre.setText(itemList.get(listPosition).getTitre());
        holder.qcmRefType.setText(itemList.get(listPosition).getType_ref());
        holder.qcmRef.setText(itemList.get(listPosition).getRef());
        holder.qcmMatiereId.setText(itemList.get(listPosition).getMatiere_id());
        holder.qcmAnneeId.setText(itemList.get(listPosition).getAnnee_id());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView qcmTitre, qcmId, qcmRefType, qcmRef,qcmMatiereId, qcmAnneeId;
        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("qcmarrayadapter", "ViewHolder ");
            itemView.setOnClickListener(this);
            this.qcmId = itemView.findViewById(R.id.qcmId);
            this.qcmTitre = itemView.findViewById(R.id.qcmTitre);
            this.qcmRefType = itemView.findViewById(R.id.qcmRefType);
            this.qcmRef = itemView.findViewById(R.id.qcmRef);
            this.qcmMatiereId = itemView.findViewById(R.id.qcmMatiereId);
            this.qcmAnneeId = itemView.findViewById(R.id.qcmAnneeId);
        }

        @Override
        public void onClick(View view) {

            Log.d("qcmarrayadapter", "onClick " + getLayoutPosition() + " " + qcmTitre.getText());

            //OPEN COURS FRAGMENT WHICH CONTAINS A WEB VIEW
            QCM qcm = new QCM(qcmId.getText().toString(), qcmTitre.getText().toString(), qcmRefType.getText().toString(), qcmRef.getText().toString(), qcmMatiereId.getText().toString(), qcmAnneeId.getText().toString());
            MatiereActivity mc = (MatiereActivity) view.getContext();
            Log.d("qcmarrayadapter", "QCM: " + qcm.getTitre());
            Log.d("qcmarrayadapter", "Matiere Activity classe: " + mc.getClass().toString() + "matiere id de l'activity: " + mc.matiere.getId());
            mc.setQCM(qcm);

            //add activity to DB
            //removed because i will only add the QCM activity to the DB once the qcm has been taken
            /*
            SharedPreferences prefs =   view.getContext().getSharedPreferences(GlobalProperties.login_sharedpreferences, view.getContext().MODE_PRIVATE);
            String user_id = prefs.getString("id", null);
            String qcm_id = this.qcmId.getText().toString();
            String type = "startedqcm";
            String act_text = view.getContext().getResources().getString(R.string.activityQcmTextmessage) + " " + this.qcmTitre.getText().toString();
            ActivityDB act = new ActivityDB(user_id, type, qcm_id, act_text);
            AddActivityToDBTask addActDb = new AddActivityToDBTask();
            addActDb.execute(act);
            */
            QCMFragment qcmFragment = new QCMFragment();
            mc.replaceFragment(qcmFragment);

        }
    }
}
