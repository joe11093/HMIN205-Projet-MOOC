package com.example.joseph.mooc.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.mooc.Activities.MatiereActivity;
import com.example.joseph.mooc.BackgroundTasks.GetAllTask;
import com.example.joseph.mooc.BackgroundTasks.GetQuestionsOfQuizTask;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.Matiere;
import com.example.joseph.mooc.Models.QCM;
import com.example.joseph.mooc.Models.QuestionsReponsesQCM;
import com.example.joseph.mooc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class QCMFragment extends Fragment implements Callback {
    MatiereActivity calling_activity;
    Matiere matiere;
    QCM qcm;
    TextView QCMTitre;
    TextView QCMid;
    ArrayList<QuestionsReponsesQCM> questionsReponsesQCMs;
    LinearLayout linearLayout;
    ScrollView qcmScrollView;
    ArrayList<RadioGroup> radioGroups;
    ArrayList<Integer> reponsesutil;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("QCMFrag", "ONCREATE");
        super.onCreate(savedInstanceState);
        this.calling_activity = (MatiereActivity) getActivity();
        this.matiere = this.calling_activity.matiere;
        this.qcm = this.calling_activity.qcm;
        this.questionsReponsesQCMs = new ArrayList<QuestionsReponsesQCM>();
        this.radioGroups = new ArrayList<RadioGroup>();



        GetQuestionsOfQuizTask getQuestionsOfQuizTask = new GetQuestionsOfQuizTask(this);
        getQuestionsOfQuizTask.execute(qcm.getId());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qcm, container, false);
        this.linearLayout = view.findViewById(R.id.qcmLinearLayout);
        this.qcmScrollView = view.findViewById(R.id.qcmScrollView);
        //this.QCMTitre = view.findViewById(R.id.qcmQuizTitre);
        //this.QCMid = view.findViewById(R.id.qcmQuizId);
        //QCMTitre.setText(this.qcm.getTitre());
        //QCMid.setText(this.qcm.getId());
        return view;
    }


    public void initQCM(){
        Log.d("QCMFrag", "INIT QCM");
        //linearLayout = new LinearLayout(getContext());
        //linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        Log.d("QCMFrag", ""+this.questionsReponsesQCMs.size());
        Log.d("QCMFrag", "radiigroups size: "+this.radioGroups.size());
        this.reponsesutil = new ArrayList<Integer>();

        for(int i = 0; i < this.questionsReponsesQCMs.size(); i++){
            this.radioGroups.add(i, new RadioGroup(getContext()));
        }
        Log.d("QCMFrag", ""+this.questionsReponsesQCMs.size());
        for(int i = 0; i < this.questionsReponsesQCMs.size(); i++){

            this.radioGroups.get(i).setId(i);
            RadioButton[] rb = new RadioButton[3];
            this.radioGroups.get(i).setOrientation(RadioGroup.VERTICAL);
            TextView tv = new TextView(getContext());
            this.radioGroups.get(i).addView(tv);
            tv.setText(i + ". " + this.questionsReponsesQCMs.get(i).getQuestion());

            rb[0] = new RadioButton(getContext());
            rb[0].setText(this.questionsReponsesQCMs.get(i).getOptions().get(0));
            //rb[0].setId(0);
            this.radioGroups.get(i).addView(rb[0]);

            rb[1] = new RadioButton(getContext());
            rb[1].setText(this.questionsReponsesQCMs.get(i).getOptions().get(1));
            //rb[1].setId(1);
            this.radioGroups.get(i).addView(rb[1]);

            rb[2] = new RadioButton(getContext());
            rb[2].setText(this.questionsReponsesQCMs.get(i).getOptions().get(2));
            //rb[2].setId(2);
            this.radioGroups.get(i).addView(rb[2]);

            this.linearLayout.addView(this.radioGroups.get(i));

            Log.d("rgchildren", "" + this.radioGroups.get(i).getChildCount());
            //this.linearLayout.addView(rg);
            //this.radioGroups.add(rg);
        }
        Button b = new Button(getContext());
        b.setText(getResources().getString(R.string.submit));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "OnClickQCM", Toast.LENGTH_SHORT).show();
                correctQCM();
            }
        });
        this.linearLayout.addView(b);
        //this.qcmScrollView.addView(linearLayout);
        Log.d("QCMFrag", "Size of the array of radiogrous: " + this.radioGroups.size());
    }

    public void correctQCM(){


        for(int i = 0; i < radioGroups.size(); i++){
            int id = radioGroups.get(i).getCheckedRadioButtonId();
            RadioButton r = getView().findViewById(id);
            int idx = radioGroups.get(i).indexOfChild(r);
            this.reponsesutil.add(idx);
            Log.d("QCMFrag", "position: " + idx);
        }
        for(int i = 0; i < radioGroups.size(); i++){
            this.radioGroups.get(i).setId(i);
            RadioButton[] rb = new RadioButton[3];
            this.radioGroups.get(i).setOrientation(RadioGroup.VERTICAL);
            TextView tv = new TextView(getContext());
            this.radioGroups.get(i).addView(tv);
            tv.setText(i + ". " + this.questionsReponsesQCMs.get(i).getQuestion());

            for(int k = 0; i < 3; i++){
                rb[k] = new RadioButton(getContext());
                rb[k].setText(this.questionsReponsesQCMs.get(i).getOptions().get(0));
                //rb[0].setId(0);
                this.radioGroups.get(i).addView(rb[0]);
            }

            this.radioGroups.get(i).addView(rb[2]);

            this.linearLayout.addView(this.radioGroups.get(i));

            Log.d("rgchildren", "" + this.radioGroups.get(i).getChildCount());
            //this.linearLayout.addView(rg);
            //this.radioGroups.add(rg);
        }
        Log.d("QCMFrag", "Size of the array of radiogrous: " + this.radioGroups.size());
    }

    @Override
    public void processData(String code, String data) {
        if (code.equals("QuestionsOfQuizTask") && data != null) {
            Log.d("QCMFrag", "on process... data: " + data);
            if (!data.equals("no_results") && !data.equals("error")) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject quest = jsonArray.getJSONObject(i);
                        String id = quest.getString("id");
                        String qcm_id = quest.getString("qcm_id");
                        String questionstr = quest.getString("question");
                        ArrayList<String> rep = new ArrayList<>();
                        rep.add(quest.getString("option1"));
                        rep.add(quest.getString("option2"));
                        rep.add(quest.getString("option3"));
                        int correct = quest.getInt("correct_answer");
                        QuestionsReponsesQCM qrqcm = new QuestionsReponsesQCM(id, qcm_id, questionstr, rep, correct);
                        this.questionsReponsesQCMs.add(qrqcm);
                    }
                    Log.d("QCMFrag", "printing first element of Question List: " + questionsReponsesQCMs.get(0).getId() + ": " + questionsReponsesQCMs.get(0).getQuestion());

                   initQCM();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
