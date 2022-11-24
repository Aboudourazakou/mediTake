package com.example.meditake.models;

/***
 "Created by  TETEREOU Aboudourazakou on "11/23/2022
 "Project name "MediTake
 */
public class Medicament {

    private  String nom;
    private  int qte;
    private  int categorieId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }
}
