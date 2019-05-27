package com.example.joseph.mooc.Fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
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
import com.example.joseph.mooc.BackgroundTasks.AddActivityToDBTask;
import com.example.joseph.mooc.BackgroundTasks.GetAllTask;
import com.example.joseph.mooc.BackgroundTasks.GetQuestionsOfQuizTask;
import com.example.joseph.mooc.Helper.GlobalProperties;
import com.example.joseph.mooc.Interfaces.Callback;
import com.example.joseph.mooc.Models.ActivityDB;
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
    TextView QCMTitre, TextView, QCMid, QCMtoptext;
    ArrayList<QuestionsReponsesQCM> questionsReponsesQCMs;
    LinearLayout linearLayout;
    ScrollView qcmScrollView;
    ArrayList<RadioGroup> radioGroups;
    ArrayList<Integer> reponsesutil;
    Button b;


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
        this.QCMtoptext = view.findViewById(R.id.qcmTopText);
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
        for(int i = 0; i < this.radioGroups.size(); i++){

            this.radioGroups.get(i).setId(View.generateViewId());
            RadioButton[] rb = new RadioButton[3];
            this.radioGroups.get(i).setOrientation(RadioGroup.VERTICAL);
            TextView tv = new TextView(getContext());
            this.radioGroups.get(i).addView(tv);
            tv.setText(i + ". " + this.questionsReponsesQCMs.get(i).getQuestion());

            rb[0] = new RadioButton(getContext());
            rb[0].setText(this.questionsReponsesQCMs.get(i).getOptions().get(0));
            rb[0].setChecked(true);
            rb[0].setId(View.generateViewId());
            this.radioGroups.get(i).addView(rb[0]);

            rb[1] = new RadioButton(getContext());
            rb[1].setText(this.questionsReponsesQCMs.get(i).getOptions().get(1));
            rb[1].setId(View.generateViewId());
            this.radioGroups.get(i).addView(rb[1]);

            rb[2] = new RadioButton(getContext());
            rb[2].setText(this.questionsReponsesQCMs.get(i).getOptions().get(2));
            rb[2].setId(View.generateViewId());
            this.radioGroups.get(i).addView(rb[2]);

            this.linearLayout.addView(this.radioGroups.get(i));

            Log.d("rgchildren", "" + this.radioGroups.get(i).getChildCount());
            //this.linearLayout.addView(rg);
            //this.radioGroups.add(rg);
        }
        this.b = new Button(getContext());
        b.setText(getResources().getString(R.string.submit));
        b.setGravity(Gravity.CENTER);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "OnClickQCM", Toast.LENGTH_SHORT).show();
                correctQCM();
            }
        });
        this.linearLayout.addView(b);
        //this.qcmScrollView.addView(linearLayout);
        Log.d("QCMFrag", "Size of the array of radiogrous: " + this.radioGroups.size());
    }

    public void correctQCM(){
        Log.d("QCMFrag", "correct QCM");
        int numberCorrectAnswers = 0;
        int numberIncorrectAnswers = 0;

        boolean allcorrect = true;
        for(int i = 0; i < this.radioGroups.size(); i++){
            RadioButton checkByUser =  getView().findViewById(radioGroups.get(i).getCheckedRadioButtonId());
            int posRBChecked = ((RadioGroup)checkByUser.getParent()).indexOfChild(checkByUser);
            int correctAnswer =  this.questionsReponsesQCMs.get(i).getCorrect_answer();
            if(posRBChecked == correctAnswer){
                numberCorrectAnswers++;
                checkByUser.setTextColor(Color.GREEN);
            }
            else{
                numberIncorrectAnswers++;
                checkByUser.setTextColor(Color.RED);
                RadioButton correctRB =  (RadioButton) radioGroups.get(i).getChildAt(correctAnswer);
                correctRB.setTextColor(Color.GREEN);
                allcorrect = false;
            }
            if(allcorrect){
                this.QCMtoptext.setTextColor(Color.GREEN);
                this.QCMtoptext.setText(getString(R.string.quizCorrect));
            }
            else{
                String singOrPlur;
                //prepare string to display
                if(numberCorrectAnswers == 1)
                    singOrPlur = "";
                else
                    singOrPlur = "s";

                this.QCMtoptext.setTextColor(Color.RED);
                this.QCMtoptext.setText(getString(R.string.quizIncorrect, numberCorrectAnswers, singOrPlur, this.questionsReponsesQCMs.size()));
            }
            qcmScrollView.fullScroll(ScrollView.FOCUS_UP);
            this.b.setText(getString(R.string.finishQuiz));

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(QCMFragment.this);
                    transaction.commit();
                    int mainact_children = calling_activity.linearLayout.getChildCount();
                    Log.d("QCMFrag", "matiere_activity children count: " + mainact_children);
                    for(int i = 0; i < mainact_children; i++){
                        calling_activity.linearLayout.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                    fragmentManager.popBackStack();
                    fragmentManager.popBackStack();
                }
            });//end of loop
        }

        //adding the activity to the db
        addActivity(numberCorrectAnswers);

    }

    public void addActivity(int correct){
        //add activity to DB
        SharedPreferences prefs =   getContext().getSharedPreferences(GlobalProperties.login_sharedpreferences, getContext().MODE_PRIVATE);
        String user_id = prefs.getString("id", null);
        String qcm_id = this.qcm.getId();
        String type = "qcm";
        String score = "" + correct + "/"+this.questionsReponsesQCMs.size();
        String act_text = getContext().getResources().getString(R.string.activityQcmTextmessage, this.qcm.getTitre(), score);
        ActivityDB act = new ActivityDB(user_id, type, this.qcm.getId(), act_text);
        AddActivityToDBTask addActDb = new AddActivityToDBTask();
        addActDb.execute(act);
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
