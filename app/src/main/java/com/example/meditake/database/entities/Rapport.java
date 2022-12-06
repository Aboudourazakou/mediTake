package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {@ForeignKey(entity = Rappel.class, parentColumns = "id", childColumns = "idRappel")})
public class Rapport {
    @PrimaryKey
    private  Long id;
    private  String message;
    private  String statut;
    private  long date;
    private long idRappel;



    public Rapport() {
    }


    public long getIdRappel() {
        return idRappel;
    }

    public void setIdRappel(long idRappel) {
        this.idRappel = idRappel;
    }

    public Rapport(String message, String statut, long date, long idPatient, long idRappel) {
        this.message = message;
        this.statut = statut;
        this.date = date;

        this.idRappel = idRappel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rapport{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", statut='" + statut + '\'' +
                ", date=" + date +

                ", idRappel=" + idRappel +
                '}';
    }
}
