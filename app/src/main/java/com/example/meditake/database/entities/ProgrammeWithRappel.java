package com.example.meditake.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProgrammeWithRappel {

    @Embedded
    private Programme programme;

    @Relation(parentColumn = "id",entityColumn = "programmeId")
    private List<Rappel> rappels;

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
    }

    public List<Rappel> getRappels() {
        return rappels;
    }

    public void setRappels(List<Rappel> rappels) {
        this.rappels = rappels;
    }

    @Override
    public String toString() {

        String programmeWithRappel = "ProgrammeWithRappel{" +
                "programme=" + programme +
                ",\n rappels : \n";

        int i=0;
        for (Rappel r:
             rappels) {
            programmeWithRappel+= "rappel["+i+"]"+ " = " + r.toString() +"\n";
            i++;
        }

        programmeWithRappel+= '}';

        return programmeWithRappel;
    }


}
