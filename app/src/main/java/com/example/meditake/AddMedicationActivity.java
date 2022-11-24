package com.example.meditake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddMedicationActivity extends AppCompatActivity {



    LinearLayout partie2,buttons ,mDateBegin;
    Button btnNext , btnNext2 , btnBarSave,btnOtherOptions , btnSaveFirst , btnAEffacer;
    Spinner spnFrequence;
    EditText medName;
    ImageButton btnClose;
    LinearLayout addTitle , otherOptionView;
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
        btnOtherOptions = findViewById(R.id.other_option);
        btnSaveFirst = findViewById(R.id.btn_save_first);
        otherOptionView = findViewById(R.id.other_option_view);

        mDateBegin = findViewById(R.id.date_begin);
        btnAEffacer = findViewById(R.id.login);

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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(AddMedicationActivity.this, android.R.layout.simple_spinner_item, frequences);

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

        mDateBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarAlertDialog();
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

        btnOtherOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOtherOptions.setVisibility(View.GONE);
                btnSaveFirst.setVisibility(View.GONE);
                otherOptionView.setVisibility(View.VISIBLE);
            }
        });

        btnAEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
    }


   private void showCalendarAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.activity_calendar, null);
        CalendarView   mCalendarView = (CalendarView) customLayout.findViewById(R.id.calendar_id);
        TextView  myear = (TextView) customLayout.findViewById(R.id.txtview_year);
        TextView mdaysMonth = (TextView) customLayout.findViewById(R.id.days_month);
        TextView  texte = (TextView) customLayout.findViewById(R.id.text_view);


       Date date = new Date();
       Date current = Calendar.getInstance().getTime();
       int days = current.getDate();


       mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
               date.setYear(i);
               date.setMonth(i1);
               date.setDate(i2);


               myear.setText(""+i);

               SimpleDateFormat form = new SimpleDateFormat("EEE. dd MMM.");
               String daysMonth = form.format(date);
               mdaysMonth.setText(daysMonth);

               SimpleDateFormat formatter = new SimpleDateFormat("dd MMM");
               String  date_choice = formatter.format(date);

               if(i2==days-1)
                   texte.setText("Hier, "+date_choice);
               else if(i2==days)
                   texte.setText("Aujourd'hui, "+date_choice);
               else if(i2==days+1)
                   texte.setText("Demain, "+date_choice);
               else
                   texte.setText(date_choice);


           }
       });

       alertDialog.setView(customLayout);
       AlertDialog dialog = alertDialog.create();
       dialog.show();


    }

   private void checkLogin(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(activity_login.SHARED_PREF_NAME,MODE_PRIVATE);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
                if(hasLoggedIn){
                    Intent intent = new Intent(AddMedicationActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(AddMedicationActivity.this,activity_login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
   }


}