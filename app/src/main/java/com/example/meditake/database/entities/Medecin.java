package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Medecin")
public class Medecin extends Utilisateur{
    @PrimaryKey
    private Long id;

    public Medecin(Long id,String prenom, String nom, String motDePasse, String login) {
        super(prenom, nom, motDePasse, login);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
