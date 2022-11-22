package com.example.meditake.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProgrammeWithRappel {

    @Embedded
    Programme programme;

    @Relation(parentColumn = "id",entityColumn = "programmeId")
    List<Rappel> rappels;

    public ProgrammeWithRappel(Programme programme, List<Rappel> rappels) {
        this.programme = programme;
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
