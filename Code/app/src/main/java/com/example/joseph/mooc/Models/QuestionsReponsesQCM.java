package com.example.joseph.mooc.Models;

import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class QuestionsReponsesQCM {
    String id;
    String qcm_id;
    String question;
    ArrayList<String> options;
    int correct_answer;

    public QuestionsReponsesQCM(){

    }
    public QuestionsReponsesQCM(String id, String qcm_id, String question, ArrayList<String> options, int correct_answer) {
        this.id = id;
        this.qcm_id = qcm_id;
        this.question = question;
        this.options = options;
        this.correct_answer = correct_answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQcm_id() {
        return qcm_id;
    }

    public void setQcm_id(String qcm_id) {
        this.qcm_id = qcm_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }
}
