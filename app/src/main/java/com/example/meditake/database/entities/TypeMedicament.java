package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TypeMedicament {
    @PrimaryKey
    private int id;

    private  String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
