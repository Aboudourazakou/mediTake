package com.example.meditake.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RappelWithRapportAndMedicament {
    @Embedded
    private Rappel rappel;

    @Relation(parentColumn = "id",entityColumn = "idRappel")
    private List<Rapport> rapports;

    @Relation(parentColumn = "medicamentId",entityColumn = "id")
    private Medicament medicament;

    public Rappel getRappel() {
        return rappel;
    }

    public void setRappel(Rappel rappel) {
        this.rappel = rappel;
    }

    public List<Rapport> getRapports() {
        return rapports;
    }

    public void setRapports(List<Rapport> rapports) {
        this.rapports = rapports;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    @Override
    public String toString() {

        String rappelWrapport = "Rappel{" +
                "\nRappel=" + rappel +
                ",\nmedicament="+ medicament+
                ",\n rapports : \n";


        if (rapports==null) return rappelWrapport;
        int i=0;
        for (Rapport r:
                rapports) {
            rappelWrapport+= "rapport["+i+"]"+ " = " + r.toString() +"\n";
            i++;
        }

        rappelWrapport+= '}';

        return rappelWrapport;
    }
}
