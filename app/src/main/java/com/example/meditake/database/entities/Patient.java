package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Patient extends Utilisateur{
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Patient(String prenom, String nom, String motDePasse, int id) {
        super(prenom, nom, motDePasse);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
