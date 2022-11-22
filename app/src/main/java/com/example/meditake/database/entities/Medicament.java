package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity
public class Medicament {
    @PrimaryKey
    private  int id;

    private String nom;

    private String img;

    private int qte;

    private int typeMedicamentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getTypeMedicamentId() {
        return typeMedicamentId;
    }

    public void setTypeMedicamentId(int typeMedicamentId) {
        this.typeMedicamentId = typeMedicamentId;
    }
}
