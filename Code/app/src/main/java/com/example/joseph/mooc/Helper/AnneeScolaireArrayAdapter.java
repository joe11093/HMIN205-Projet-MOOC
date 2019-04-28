package com.example.joseph.mooc.Helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joseph.mooc.Models.AnneeScolaire;
import com.example.joseph.mooc.R;

import java.util.ArrayList;

/**
 * Created by josep on 4/28/2019.
 */

public class AnneeScolaireArrayAdapter extends ArrayAdapter<AnneeScolaire> {


    private Context context;
    private AnneeScolaire[] values;

    public AnneeScolaireArrayAdapter(Context context, int textViewResourceId, AnneeScolaire[] anneeScolaires){
        super(context, textViewResourceId, anneeScolaires);
        this.context = context;
        this.values = anneeScolaires;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Nullable
    @Override
    public AnneeScolaire getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getAnneescolaire());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getAnneescolaire());

        return label;
    }


}