package com.example.joseph.mooc.Models;

import java.io.Serializable;

/**
 * Created by josep on 5/22/2019.
 * Classe qui represente une matiere appartenante a une annee scolaire
 */

public class Matiere implements Serializable{
    String id;
    String titre;
    String annee_id;


    public Matiere(String id, String titre, String annee_id) {
        this.id = id;
        this.titre = titre;
        this.annee_id = annee_id;
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

    public String getAnnee_id() {
        return annee_id;
    }

    public void setAnnee_id(String annee_id) {
        this.annee_id = annee_id;
    }

    @Override
    public String toString() {
        return this.getTitre();
    }
}
