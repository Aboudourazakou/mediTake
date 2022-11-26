package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.CategorieMedicamentDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.PatientDao;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.RapportDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Patient;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.ProgrammeWithRappelWithRapportAndMedicament;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        test();

                    }

                }).start();
            }
        });
    }


    void test(){


        AppDatabase db = AppDatabase.getDataBase(getApplicationContext());



        PatientDao medecinDao = db.patientDao();
        List<Patient> medecins = new ArrayList<>(Arrays.asList(new Patient(1L,"Kodjo","Godwin","1234","medecin2"),new Patient(2L,"Amavi","Adjo","1234","medecin1")));
       medecins.stream().forEach(m->{
           if (medecinDao.getById(m.getId())==null){
               Log.e("INSERTION : ", String.valueOf(m));
               medecinDao.insert(m);
           }
       });

        CategorieMedicamentDao typeMedicamentDao = db.categorieMedicamentDao();

        typeMedicamentDao.insertAll(new CategorieMedicament(1L,"pillule"));

        MedicamentDao medicamentDao = db.medicamentDao();

      /*  medicamentDao.insertAll(new Medicament(1L,"Paracetamol","para.jpg",20,1),
                new Medicament(2L,"Peneciline","pene.jpg",20,1),
                new Medicament(3L,"Acotsi","acotsi.jpg",20,1),
                new Medicament(4L,"Jumbo","jumbo.jpg",20,1));*/

        ProgrammeDao programmeDao = db.programmeDao();

        programmeDao.insertAll(new Programme(1L,2,30,20,"Lundi Mardi Vendredi"),
                new Programme(2L,2,30,20,"Lundi Samedi Vendredi"));


        RappelDao rappelDao = db.rappelDao();

        rappelDao.insertAll(
                new Rappel(1L,1,13,12,"Prend ton medicament","lun. 21 nov a 18h45",1L,1L),
                new Rappel(3L,1,13,12,"Prend ton medicament","lun. 21 nov a 18h45",2L,1L)
                                );

        RapportDao rapportDao = db.rapportDao();

        rapportDao.insertAll(new Rapport("Papa","Pas encore",2L,1L,1L),
                new Rapport("Papamou","Pas",2L,1L,1L));


        //ProgrammeDao programmeDao = db.programmeDao();


        ProgrammeWithRappelWithRapportAndMedicament programme = programmeDao.getProgramme(1L);


        programme.getRappels().forEach(r->r.getRapports().forEach(ra->Log.e("TAGGGG : ", "Rapport: "+ra )));
    }
}