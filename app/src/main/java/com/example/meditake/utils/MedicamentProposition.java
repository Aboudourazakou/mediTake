package com.example.meditake.utils;

import com.example.meditake.R;

import java.util.Arrays;
import java.util.List;

public class MedicamentProposition {
    private long id;
    private int img;
    private String nom;

    private static List<MedicamentProposition> medicaments;

    public MedicamentProposition(long id,int img, String nom) {
        this.id=id;
        this.img = img;
        this.nom = nom;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static List<MedicamentProposition> getMedicaments(){
        return medicaments;
    }

    public static void initMedicaments(){
        medicaments.addAll(Arrays.asList(new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
         new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline")));

    }
}
