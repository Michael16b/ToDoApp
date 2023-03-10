package com.example.todomobile;

public class Cinema {

    /**chemin vers l'affiche*/
    private String cheminAffiche;
    /**Titre du film*/
    private String titre;
    /**Nom du réalisateur*/
    private String nomRealisateur;
    /**Durée du film en minutes*/
    private int duree;

    public Cinema(String affiche, String title, String real, int duree){

        if(title != null && real != null && duree >= 15){
            this.cheminAffiche = affiche;
            this.titre = title;
            this.nomRealisateur = real;
            this.duree = duree;
        }
    }

    public String getCheminAffiche() {
        return cheminAffiche;
    }

    public String getTitre() {
        return titre;
    }

    public String getNomRealisateur() {
        return nomRealisateur;
    }

    public int getDuree() {
        return duree;
    }




    public void setCheminAffiche(String chemin){
        this.cheminAffiche = chemin;
    }

    public void setTitre(String titre){
        this.titre = titre;
    }

    public void setNomRealisateur(String nomRealisateur){
        this.nomRealisateur = nomRealisateur;
    }

    public void setDuree(int duree){
        this.duree = duree;
    }

}
