package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import kotlin.jvm.Transient;


@Entity
public class Programme {
    @PrimaryKey
    private Long id;

    private int heure;

    private int minutes;

    private int duree;

    private String jours;
    @Ignore
    List<Rappel> rappelList;




    private long idPatient;


    public Programme(Long id, int heure, int minutes, int duree, String jours) {
        this.id = id;
        this.heure = heure;
        this.minutes = minutes;
        this.duree = duree;
        this.jours = jours;
    }

    public Programme() {
    }

    public Programme(int heure, int minutes, int duree, String jours,long idPatient) {
        this.heure = heure;
        this.minutes = minutes;
        this.duree = duree;
        this.jours = jours;
        this.idPatient = idPatient;
    }



    public List<Rappel> getRappelList() {
        return rappelList;
    }

    public void setRappelList(List<Rappel> rappelList) {
        this.rappelList = rappelList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getJours() {
        return jours;
    }

    public void setJours(String jours) {
        this.jours = jours;
    }

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "id=" + id +
                ", heure=" + heure +
                ", minutes=" + minutes +
                ", duree=" + duree +
                ", jours='" + jours + '\'' +
                ", rappelList=" + rappelList +
                ", idPatient=" + idPatient +
                '}';
    }
}
