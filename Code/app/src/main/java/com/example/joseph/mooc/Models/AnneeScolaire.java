package com.example.joseph.mooc.Models;

/**
 * Created by josep on 4/27/2019.
 */

public class AnneeScolaire {
    private int id;
    private String anneescolaire;

    public AnneeScolaire(int id, String anneescolaire) {
        this.id = id;
        this.anneescolaire = anneescolaire;
    }


    public int getId() {
        return id;
    }

    public String getAnneescolaire() {
        return anneescolaire;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setAnneescolaire(String anneescolaire) {
        this.anneescolaire = anneescolaire;
    }
}
