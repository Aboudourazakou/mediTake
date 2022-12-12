package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        db = AppDatabase.getDataBase(getApplicationContext());
       test();
    }



    void test(){

        CategorieMedicamentDao typeMedicamentDao = db.categorieMedicamentDao();
        typeMedicamentDao.insertAll(new CategorieMedicament(1L,"pillule"), new CategorieMedicament(2L,"Goutes ophtalmiques"), new CategorieMedicament(3L,"injections"));
        addMedicaments();

    }

    void addMedicaments(){

        MedicamentDao medicamentDao = db.medicamentDao();
        Medicament medicament=new Medicament();
        medicament.setQte(34);
        medicament.setCategorieId(1);
        medicament.setNom("Paracetamol");

        Drawable drawable=getResources().getDrawable(R.drawable.logo);
        BitmapDrawable bitDw = ((BitmapDrawable) drawable);
        Bitmap bitmap = bitDw.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        medicament.setImage(imageInByte);

        medicamentDao.insertAll(medicament);

    }
}