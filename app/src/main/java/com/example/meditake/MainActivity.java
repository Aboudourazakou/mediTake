package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

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
import com.example.meditake.database.dto.UtilisateurLogin;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.ProgrammeWithRappel;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.TypeMedicament;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,activity_login.class));
                finish();
            }
        },1000);


    }


    void httpCallToBackendService(){
        UtilisateurService service = RetrofitGenerator.getRetrofit().create(UtilisateurService.class);

        Call<Medecin> call = service.logMedecinIn(new UtilisateurLogin("username1","password1"));

        call.enqueue(new Callback<Medecin>() {
            @Override
            public void onResponse(Call<Medecin> call, Response<Medecin> response) {
                int statusCode = response.code();
                Medecin medecin = response.body();

                System.out.println(medecin);
            }

            @Override
            public void onFailure(Call<Medecin> call, Throwable t) {
                // Log error here since request failed

                System.out.println(t.getMessage());
            }
        });

     Call<List<Medecin>> medecinsCall = service.getAll();

       medecinsCall.enqueue(new Callback<List<Medecin>>() {
           @Override
           public void onResponse(Call<List<Medecin>> call, Response<List<Medecin>> response) {
               List<Medecin> medecins = response.body();

               medecins.forEach(p-> Log.e("REQUETE",String.valueOf(p)));
           }

           @Override
           public void onFailure(Call<List<Medecin>> call, Throwable t) {
               System.out.println(t.getMessage());
           }
       });



    }

    void testDatabase(){

        AppDatabase db = AppDatabase.getDataBase(getApplicationContext());


        /*MedecinDao medecinDao = db.medecinDao();

        List<Medecin> medecins = new ArrayList<>(Arrays.asList(new Medecin(1L,"Kodjo","Godwin","1234","medecin2"),new Medecin(2L,"Amavi","Adjo","1234","medecin1")));

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