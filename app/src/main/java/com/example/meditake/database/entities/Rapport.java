package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(foreignKeys = @ForeignKey(entity=Patient.class, parentColumns="id", childColumns="idPatient"))
public class Rapport {
    private  Long id;
    private  String message;
    private  String statut;
    private  long date;
    private  long idPatient;

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

    public long getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(long idPatient) {
        this.idPatient = idPatient;
    }
}
