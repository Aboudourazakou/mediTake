package com.example.meditake.models;

/***
 "Created by  TETEREOU Aboudourazakou on "11/21/2022
 "Project name "MediTake
 */
public class Rappel {
    private  int heure;
    private  int minutes;
    private  int  qtePilule;
    private String message;
    private String statut;
    private  Medicament medicament;
    private  String reschedulemsg;
    private  String ignoreMsg;

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
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

    public int getQtePilule() {
        return qtePilule;
    }

    public void setQtePilule(int qtePilule) {
        this.qtePilule = qtePilule;
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
}
