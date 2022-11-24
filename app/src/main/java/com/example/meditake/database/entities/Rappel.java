package com.example.meditake.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity
public class Rappel {
    @PrimaryKey
    private Long id;

    private Long date;

    private int nbrPillule;

    private boolean active;

    private int nbrDeFois;

    private Long programmeId;

    private boolean statut;

    private String message;

    private long medicamentId;

    public Rappel() {
    }

    public Rappel(Long date, int nbrPillule, boolean active, int nbrDeFois, Long programmeId, boolean statut, String message, long medicamentId) {
        this.date = date;
        this.nbrPillule = nbrPillule;
        this.active = active;
        this.nbrDeFois = nbrDeFois;
        this.programmeId = programmeId;
        this.statut = statut;
        this.message = message;
        this.medicamentId = medicamentId;
    }

    public Rappel(Long id, Long date, int nbrPillule, boolean active, int nbrDeFois, Long programmeId, boolean statut, String message, long medicamentId) {
        this.id = id;
        this.date = date;
        this.nbrPillule = nbrPillule;
        this.active = active;
        this.nbrDeFois = nbrDeFois;
        this.programmeId = programmeId;
        this.statut = statut;
        this.message = message;
        this.medicamentId = medicamentId;
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Long getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(Long programmeId) {
        this.programmeId = programmeId;
    }

    @Override
    public String toString() {
        return "Rappel{" +
                "id=" + id +
                ", date=" + date +
                ", nbrPillule=" + nbrPillule +
                ", active=" + active +
                ", nbrDeFois=" + nbrDeFois +
                ", programmeId=" + programmeId +
                ", statut=" + statut +
                ", message='" + message + '\'' +
                '}';
    }
}
