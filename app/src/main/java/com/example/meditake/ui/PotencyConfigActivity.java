package com.example.meditake.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.meditake.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PotencyConfigActivity extends AppCompatActivity {

    Spinner unitePuissanceMed;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potency_config);

        unitePuissanceMed = findViewById(R.id.spinner_potency_unit);

        ArrayList<String> potencyUnitList = new ArrayList<>();
        potencyUnitList.addAll(Arrays.asList(new String[]{"mg", "g"}));

        ArrayAdapter<String> potencyAdapter = new ArrayAdapter<>(PotencyConfigActivity.this, android.R.layout.simple_spinner_item,potencyUnitList);
        potencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitePuissanceMed.setAdapter(potencyAdapter);
    }
}