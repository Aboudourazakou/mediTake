package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class  Rappel {
    @PrimaryKey
    private int id;

    private String heure;

    private int nbrPillule;

    private boolean active;

    private int nbrDeFois;

    private int medicamentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getNbrPillule() {
        return nbrPillule;
    }

    public void setNbrPillule(int nbrPillule) {
        this.nbrPillule = nbrPillule;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNbrDeFois() {
        return nbrDeFois;
    }

    public void setNbrDeFois(int nbrDeFois) {
        this.nbrDeFois = nbrDeFois;
    }

    public int getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(int medicamentId) {
        this.medicamentId = medicamentId;
    }
}
