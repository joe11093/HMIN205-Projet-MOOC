package com.example.joseph.mooc.Helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.BackgroundTasks.AddActivityToDBTask;
import com.example.joseph.mooc.Fragments.FicheDeCoursFragment;
import com.example.joseph.mooc.Models.ActivityDB;
import com.example.joseph.mooc.Models.FicheDeCours;
import com.example.joseph.mooc.R;

import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class FicheDeCoursArrayAdapter extends RecyclerView.Adapter<FicheDeCoursArrayAdapter.ViewHolder>{

    private int listItemLayout;
    private ArrayList<FicheDeCours> itemList;
    public MatiereActivity matiereActivity;

    // Constructor of the class
    public FicheDeCoursArrayAdapter(MatiereActivity matiereActivity, int layoutId, ArrayList<FicheDeCours> itemList) {
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
    public FicheDeCoursArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        FicheDeCoursArrayAdapter.ViewHolder myViewHolder = new FicheDeCoursArrayAdapter.ViewHolder(view);
        view.setMinimumHeight(parent.getHeight()/4);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final FicheDeCoursArrayAdapter.ViewHolder holder, final int listPosition) {
        //what to do to an item in a the recycler when it appears on screen
        holder.coursTitre.setText("Ch." + itemList.get(listPosition).getChapitre() + ": " + itemList.get(listPosition).getTitre());
        holder.coursId.setText(itemList.get(listPosition).getId());
        holder.coursChapitre.setText(itemList.get(listPosition).getChapitre());
        holder.coursContenu.setText(itemList.get(listPosition).getContenu());
        holder.coursMatiereId.setText(itemList.get(listPosition).getMatiere_id());
        holder.coursAnneeId.setText(itemList.get(listPosition).getAnnee_id());

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView coursTitre, coursId, coursChapitre, coursContenu, coursMatiereId, coursAnneeId;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            coursTitre = itemView.findViewById(R.id.coursTitre);
            coursId = itemView.findViewById(R.id.coursId);
            coursChapitre = itemView.findViewById(R.id.coursChapitre);
            coursContenu = itemView.findViewById(R.id.coursContenu);
            coursMatiereId = itemView.findViewById(R.id.coursMatiereId);
            coursAnneeId = itemView.findViewById(R.id.coursAnneeId);


        }
        @Override
        public void onClick(View view) {
            Log.d("fichearrayadapter", "onClick " + getLayoutPosition() + " " + coursTitre.getText());

            //OPEN COURS FRAGMENT WHICH CONTAINS A WEB VIEW
            FicheDeCours fdc = new FicheDeCours(coursId.getText().toString(), coursChapitre.getText().toString(), coursTitre.getText().toString(), coursContenu.getText().toString(), coursMatiereId.getText().toString(), coursAnneeId.getText().toString());
            MatiereActivity mc = (MatiereActivity) view.getContext();
            Log.d("fichearrayadapter", "Fiche de cours: " + fdc.getTitre());
            Log.d("fichearrayadapter", "Matiere Activity classe: " + mc.getClass().toString() + "matiere id de l'activity: " + mc.matiere.getId());
            mc.setFicheDeCours(fdc);

            //add activity to DB
            SharedPreferences prefs =   view.getContext().getSharedPreferences(GlobalProperties.login_sharedpreferences, view.getContext().MODE_PRIVATE);
            String user_id = prefs.getString("id", null);
            String vid_id = this.coursId.getText().toString();
            String type = "fichedecours";
            String act_text = view.getContext().getResources().getString(R.string.activityFicheTextMessage) + " " + this.coursTitre.getText().toString();
            ActivityDB act = new ActivityDB(user_id, type, vid_id, act_text);
            AddActivityToDBTask addActDb = new AddActivityToDBTask();
            addActDb.execute(act);

            FicheDeCoursFragment ficheDeCoursFragment = new FicheDeCoursFragment();
            mc.replaceFragment(ficheDeCoursFragment);
        }
    }
}

