package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity
public class  Rappel {
    @PrimaryKey
    private Long id;
    private int heure;
    private int minutes;
    private double  qtePilule;
    private String message;
    private String lastTimeTaken;
    private long medicamentId;
    private long programmeId;

    public Rappel(long id,int heure, int minutes, double qtePilule, String message, String lastTimeTaken, long medicamentId,long programmeId) {
        this.id = id;
        this.heure = heure;
        this.minutes = minutes;
        this.qtePilule = qtePilule;
        this.message = message;
        this.lastTimeTaken = lastTimeTaken;
        this.medicamentId = medicamentId;
        this.programmeId = programmeId;
    }

    public Rappel() {
    }

    public long getMedicamentId() {
        return medicamentId;
    }

    public void setMedicamentId(long medicamentId) {
        this.medicamentId = medicamentId;
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

    public double getQtePilule() {
        return qtePilule;
    }

    public void setQtePilule(double qtePilule) {
        this.qtePilule = qtePilule;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastTimeTaken() {
        return lastTimeTaken;
    }

    public void setLastTimeTaken(String lastTimeTaken) {
        this.lastTimeTaken = lastTimeTaken;
    }

    public long getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(long programmeId) {
        this.programmeId = programmeId;
    }

    @Override
    public String toString() {
        return "Rappel{" +
                "id=" + id +
                ", heure=" + heure +
                ", minutes=" + minutes +
                ", qtePilule=" + qtePilule +
                ", message='" + message + '\'' +
                ", lastTimeTaken='" + lastTimeTaken + '\'' +
                ", medicamentId=" + medicamentId +
                ", programmeId=" + programmeId +
                '}';
    }
}
