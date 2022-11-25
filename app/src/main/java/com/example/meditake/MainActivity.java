package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.meditake.database.AppDatabase;
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
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.services.RetrofitGenerator;
import com.example.meditake.services.UtilisateurService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


}