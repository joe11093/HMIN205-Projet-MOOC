package com.example.joseph.mooc.Helper;

/**
 * Created by josep on 5/23/2019.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MatiereArrayAdapter extends RecyclerView.Adapter<MatiereArrayAdapter.ViewHolder>{
    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<Matiere> itemList;
    // Constructor of the class
    public MatiereArrayAdapter(int layoutId, ArrayList<Matiere> itemList) {
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
    public MatiereArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        view.setMinimumHeight(parent.getHeight()/4);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        holder.item.setText(itemList.get(listPosition).getTitre());
        holder.itemId.setText(itemList.get(listPosition).getId());
        holder.anneeIdOfMatiere.setText(itemList.get(listPosition).getAnnee_id());

    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item, itemId, anneeIdOfMatiere;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = (TextView) itemView.findViewById(R.id.matiereItem);
            itemId = (TextView) itemView.findViewById(R.id.matiereItemId);
            anneeIdOfMatiere = (TextView) itemView.findViewById(R.id.anneeIdOfMatiere);
        }
        @Override
        public void onClick(View view) {
            Log.d("matierearrayadapter", "onClick " + getLayoutPosition() + " " + item.getText());
            int pos = getAdapterPosition();
            Log.d("matiereadapter", ""+pos);
            Intent intent = new Intent(view.getContext(), MatiereActivity.class);
            Matiere m = new Matiere(itemId.getText().toString(), item.getText().toString(), anneeIdOfMatiere.getText().toString());
            intent.putExtra("matiere", m);
            /*
            intent.putExtra("matiere", item.getText());
            intent.putExtra("matiere_id", itemId.getText());
            intent.putExtra("annee_id_of_matiere", anneeIdOfMatiere.getText());*/
            view.getContext().startActivity(intent);
        }
    }
}