package com.example.meditake.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.meditake.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AlertHourInfoActivity extends AppCompatActivity {

    Spinner typeMed;
    ArrayList<String> listTypeMed;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_hour_info);

        typeMed = findViewById(R.id.spinner_type_med);
        ArrayList<String> listTypeMed = new ArrayList<String>();
        listTypeMed.addAll(Arrays.asList(new String[]{"Pillule(s)", "Ampoule(s)", "Flacon(s)"}));

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AlertHourInfoActivity.this, android.R.layout.simple_spinner_item,listTypeMed);

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeMed.setAdapter(myAdapter);
    }
}