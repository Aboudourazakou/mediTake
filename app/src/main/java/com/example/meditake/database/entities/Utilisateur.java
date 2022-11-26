package com.example.meditake.database.entities;

public class Utilisateur {

    protected String prenom;

    protected String nom;

    protected String motDePasse;

    protected String login;

    public Utilisateur(String prenom, String nom, String motDePasse, String login) {
        this.prenom = prenom;
        this.nom = nom;
        this.motDePasse = motDePasse;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}
