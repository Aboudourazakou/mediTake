package com.example.meditake.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProgrammeWithRapportWithRappelAndMedicament {
    @Embedded
    private Programme programme;

    @Relation(entity = Rappel.class,parentColumn = "id",entityColumn = "programmeId")
    private List<RappelWithRapportAndMedicament> rappels;

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public List<RappelWithRapportAndMedicament> getRappels() {
        return rappels;
    }

    public void setRappels(List<RappelWithRapportAndMedicament> rappels) {
        this.rappels = rappels;
    }

    @Override
    public String toString() {

        String programmeEntier = "Programme {" +
                "programme = "+programme+
                ", \nrappel"+ rappels;

        programmeEntier+= '}';

        return programmeEntier;

    }
}
