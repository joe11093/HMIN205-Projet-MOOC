package com.example.joseph.mooc.Models;

import java.util.ArrayList;

/**
 * Created by josep on 5/26/2019.
 */

public class QCM {

    String id;
    String titre;
    String type_ref;
    String ref;
    String matiere_id;
    String annee_id;
    ArrayList<QuestionsReponsesQCM> questions_reponses;

    public QCM(String id, String titre, String type_ref, String ref,String matiere_id, String annee_id) {
        this.id = id;
        this.titre = titre;
        this.type_ref = type_ref;
        this.ref = ref;
        this.matiere_id = matiere_id;
        this.annee_id = annee_id;
    }

    public QCM(String id, String titre, String type_ref, String ref, String matiere_id, String annee_id,ArrayList<QuestionsReponsesQCM> questions_reponses) {
        this.id = id;
        this.titre = titre;
        this.type_ref = type_ref;
        this.ref = ref;
        this.questions_reponses = questions_reponses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType_ref() {
        return type_ref;
    }

    public void setType_ref(String type_ref) {
        this.type_ref = type_ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMatiere_id() {
        return matiere_id;
    }

    public void setMatiere_id(String matiere_id) {
        this.matiere_id = matiere_id;
    }

    public String getAnnee_id() {
        return annee_id;
    }

    public void setAnnee_id(String annee_id) {
        this.annee_id = annee_id;
    }

    public ArrayList<QuestionsReponsesQCM> getQuestions_reponses() {
        return questions_reponses;
    }

    public void setQuestions_reponses(ArrayList<QuestionsReponsesQCM> questions_reponses) {
        this.questions_reponses = questions_reponses;
    }
}
