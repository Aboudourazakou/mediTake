package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Medecin")
public class Medecin extends Utilisateur{
    @PrimaryKey(autoGenerate = true)
    private int id;

    public Medecin(String prenom, String nom, String motDePasse) {
        super(prenom, nom, motDePasse);
    }

    @Ignore
    public Medecin() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
