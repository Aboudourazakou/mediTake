package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Patient extends Utilisateur{
    @PrimaryKey
    private Long id;

    @Ignore
    public Patient(String prenom, String nom, String motDePasse,String login) {
        super(prenom, nom, motDePasse,login);
    }

    public Patient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
