package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedecinDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.ProgrammeWithRappelDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.TypeMedicamentDao;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.ProgrammeWithRappel;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.TypeMedicament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    public void run() {
                        testDatabase();
                    }
                }).start();

            }
        });
    }

    void testDatabase(){

        AppDatabase db = AppDatabase.getDataBase(getApplicationContext());

        /*MedecinDao medecinDao = db.medecinDao();

        List<Medecin> medecins = new ArrayList<>(Arrays.asList(new Medecin(1L,"Kodjo","Godwin","1234"),new Medecin(2L,"Amavi","Adjo","1234")));

       medecins.stream().forEach(m->{
           if (medecinDao.getById(m.getId())==null){
               Log.e("INSERTION : ", String.valueOf(m));
               medecinDao.insert(m);
           }
       });*/

        TypeMedicamentDao typeMedicamentDao = db.typeMedicamentDao();

        typeMedicamentDao.insertAll(new TypeMedicament(1L,"pillule"));

        MedicamentDao medicamentDao = db.medicamentDao();

        medicamentDao.insertAll(new Medicament(1L,"Paracetamol","para.jpg",20,1),
                new Medicament(2L,"Peneciline","pene.jpg",20,1),
                new Medicament(3L,"Acotsi","acotsi.jpg",20,1),
                new Medicament(4L,"Jumbo","jumbo.jpg",20,1));

        ProgrammeDao programmeDao = db.programmeDao();

        programmeDao.insertAll(new Programme(1L,2,30,20,"Lundi Mardi Vendredi"),
                new Programme(2L,2,30,20,"Lundi Samedi Vendredi"),
                new Programme(14,30,20,"Lundi Mercredi Vendredi"),
                new Programme(19,30,20,"Mardi Dimanche Jeudi"));


        RappelDao rappelDao = db.rappelDao();
        rappelDao.insertAll(new Rappel(1L,36000L,13,true,7,1L,true,"Prend on medicament",1),
                new Rappel(2L,36000L,13,true,7,1L,true,"Prend medicament",1),
                new Rappel(3L,36000L,13,true,7,1L,true,"medicament",1),
                new Rappel(4L,36000L,13,true,7,1L,true,"Prend",1));

        ProgrammeWithRappelDao programmeWithRappelDao = db.programmeWithRappelDao();
        ProgrammeWithRappel programmeWithRappel = programmeWithRappelDao.getProgrammeWithRappels(1L);

        Log.e("TAGGGG : ", "testDatabase: ¨ProgrammeWithRappel " + programmeWithRappel);
    }
}