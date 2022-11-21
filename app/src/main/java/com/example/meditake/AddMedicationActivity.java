package com.example.meditake;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class AddMedicationActivity extends AppCompatActivity {



    LinearLayout partie2,buttons;
    Button btnNext , btnNext2 , btnBarSave;
    Spinner spnFrequence;
    EditText medName;
    ImageButton btnClose;
    LinearLayout addTitle;
    CardView medIcons;

    @SuppressLint({"MissingInflatedId", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        setTitle("");

        partie2 = findViewById(R.id.page2);
        buttons = findViewById(R.id.buttons);
        btnNext=findViewById(R.id.next_button);
        btnNext2 = findViewById(R.id.btn_next2);
        spnFrequence = findViewById(R.id.spinner_frequence);
        medName = findViewById(R.id.edt_nom_med);
        btnClose = findViewById(R.id.close_btn);
        medIcons = findViewById(R.id.cardview_icon_med);
        btnBarSave = findViewById(R.id.btnbar_save);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(AddMedicationActivity.this).create();
                alertDialog.setTitle("Etes-vous sur(e)?");
                alertDialog.setMessage("Vous avez des modifications non enregistrées.\n Voulez-vous quitter sans enregistrer?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Quitter",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alertDialog.show();
            }

        });

        ArrayList<String> frequences = new ArrayList<String>();
        frequences.addAll(Arrays.asList(new String[]{"Une fois par jours", "2 fois par jour", "toutes les 12 heures"}));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddMedicationActivity.this, android.R.layout.simple_spinner_item, frequences);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnFrequence.setAdapter(dataAdapter);

        btnNext.setEnabled(false);
        btnNext.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.granite,null));
        medName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length()==0){
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.LTGRAY);
                }
                else{
                    btnNext.setEnabled(true);
                btnNext.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.round_element, null));}

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partie2.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);



            }
        });

        btnNext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttons.setVisibility(View.VISIBLE);
                medIcons.setVisibility(View.VISIBLE);
                btnNext2.setVisibility(View.GONE);
                btnBarSave.setVisibility(View.VISIBLE);
            }
        });
    }
}