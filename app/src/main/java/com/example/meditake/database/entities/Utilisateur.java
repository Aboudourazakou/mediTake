package com.example.meditake.database.entities;

public abstract class Utilisateur {

    protected String prenom;

    protected String nom;

    protected String motDePasse;

    public Utilisateur(String prenom, String nom, String motDePasse) {
        this.prenom = prenom;
        this.nom = nom;
        this.motDePasse = motDePasse;
    }

    public Utilisateur() {
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
