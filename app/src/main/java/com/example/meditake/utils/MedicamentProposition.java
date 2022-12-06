package com.example.meditake.utils;
import android.util.Log;

import com.example.meditake.R;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedicamentDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 "Created by  Godwin Kvg on "12/2/2022
 "Project name "MediTake
 */
public class MedicamentProposition {


    private long id;
    private int img;
    private String nom;
    private  byte[]image;


    private static List<MedicamentProposition> medicaments=new ArrayList<>();

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

    public static void initMedicaments(AppDatabase db){

        MedicamentDao medicamentDao = db.medicamentDao();
        List<MedicamentProposition>medicamentPropositionList=  medicamentDao.getMedicaments();


        medicaments.addAll(Arrays.asList(
                new MedicamentProposition(1L, R.drawable.pill_icon,"Peneciline"),
                new MedicamentProposition(1L, R.drawable.pill_icon,"Nivaquine"),
                new MedicamentProposition(2L, R.drawable.pill_icon,"Cloroquine"),
                new MedicamentProposition(3L, R.drawable.pill_icon,"Paracetamol"),
                new MedicamentProposition(4L, R.drawable.pill_icon,"Gloquine"),
                new MedicamentProposition(5L, R.drawable.pill_icon,"Peneciline"),
                new MedicamentProposition(6L, R.drawable.pill_icon,"Viquine")));

    }

    public static List<MedicamentProposition> getBySearchValue(String value){
        return medicaments.stream()
                .filter(m->m.getNom().toLowerCase().contains(value.trim().toLowerCase()))
                .map(p -> (MedicamentProposition) p).collect(Collectors.toList());
    }


}