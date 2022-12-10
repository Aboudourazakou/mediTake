package com.example.meditake.database.dto;

/***
 "Created by  godwin Kvg on "12/8/2022
 "Project name "MediTake
 */
public class RapportDto {
    private  String message;
    private  String statut;
    private  long date;
    private String nomMedicament;

    public RapportDto(String message, String statut, long date, String nomMedicament) {
        this.message = message;
        this.statut = statut;
        this.date = date;
        this.nomMedicament = nomMedicament;
    }

    public RapportDto() {
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

    public String getNomMedicament() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }
}
