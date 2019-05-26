package com.example.joseph.mooc.Models;

/**
 * Created by josep on 5/26/2019.
 */

public class FicheDeCours  {
    String id;
    String chapitre;
    String titre;
    String contenu;
    String matiere_id;
    String annee_id;

    public FicheDeCours(){

    }
    public FicheDeCours(String id, String chapitre, String titre, String contenu, String matiere_id, String annee_id) {
        this.id = id;
        this.chapitre = chapitre;
        this.titre = titre;
        this.contenu = contenu;
        this.matiere_id = matiere_id;
        this.annee_id = annee_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChapitre() {
        return chapitre;
    }

    public void setChapitre(String chapitre) {
        this.chapitre = chapitre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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
}