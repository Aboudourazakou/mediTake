package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedecinDao;
import com.example.meditake.database.entities.Medecin;
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


        MedecinDao medecinDao = db.medecinDao();

        medecinDao.insertAll(new Medecin("Kodjo","Godwin","1234"),new Medecin("Amavi","Adjo","1234"));

        List<Medecin> medecins = medecinDao.getAll();

        medecins.stream().forEach(u->{
            Log.e("MEDECINS ",u.toString());
        });

        medecins.stream().forEach(u->{
            System.out.println(u.toString());
        });

    }
}