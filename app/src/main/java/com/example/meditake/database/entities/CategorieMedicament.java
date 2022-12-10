package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategorieMedicament {
    @PrimaryKey
    private Long id;

    private  String libelle;

    public CategorieMedicament() {
    }

    public CategorieMedicament(String libelle) {
        this.libelle = libelle;
    }

    public CategorieMedicament(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "CategorieMedicament{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
