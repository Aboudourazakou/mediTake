package com.example.meditake;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditake.adapters.DaysSelectionAdapter;
import com.example.meditake.adapters.IgnoreReasonAdapter;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.CategorieMedicamentDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.PatientDao;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Patient;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.models.Programm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class AddMedicationActivity extends AppCompatActivity {

    LinearLayout partie2,buttons ,linearLayoutDate , alertHourInfo , potencyInfo;

    Button btnNext , btnNext2 , btnBarSave,btnOtherOptions , btnSaveFirst ,save;
    TextView dateBegin,medNumber,alertHour ;
    RadioButton specificDay , allDays;

    Button btnNext , btnNext2 , btnBarSave,btnOtherOptions , btnSaveFirst ;
    TextView dateBegin;

    Spinner spnFrequence;
    EditText medName;
    ImageButton btnClose;
    LinearLayout addTitle , otherOptionView;
    CardView medIcons;
    String date_chosen;

    int hourValue,minValue;
    double medicationTakeNumber;
    String daysChosen;

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
        alertHourInfo = findViewById(R.id.med_hour_and_number);
        alertHour = findViewById(R.id.alert_hour);
        potencyInfo = findViewById(R.id.med_potency);

        hourValue=0;
        minValue=0;
        daysChosen="";
        medicationTakeNumber=0.0;

        linearLayoutDate = findViewById(R.id.date_begin_llayout);
        dateBegin = findViewById(R.id.date_begin);


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
        linearLayoutDate.setOnClickListener(new View.OnClickListener() {
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

        alertHourInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertHourInfoDialog();
            }
        });

        potencyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                definePotencyConfig();
            }
        });
      /*  if(allDays.isChecked()){
            String[]items=getResources().getStringArray(R.array.days);
            for (String item:items) {
                daysChosen = daysChosen + item;
            }
            Toast.makeText(AddMedicationActivity.this, daysChosen, Toast.LENGTH_SHORT).show();


        }*/
        allDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String[] items = getResources().getStringArray(R.array.days);
                    daysChosen="";
                    for (String item : items) {
                        daysChosen = daysChosen + item;
                    }
                    specificDay.setText("jours spécifiques de la semaine ");
                    Toast.makeText(AddMedicationActivity.this, daysChosen, Toast.LENGTH_SHORT).show();
                }


        });

        specificDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectionDaysDialog();
            }
        });


        btnAEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });


        btnBarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        saveData();

                    }

                }).start();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        saveData();

                    }

                }).start();
            }
        });

    }




    private void showCalendarAlertDialog() {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.activity_calendar, null);
        CalendarView   mCalendarView = (CalendarView) customLayout.findViewById(R.id.calendar_id);
        TextView  myear = (TextView) customLayout.findViewById(R.id.txtview_year);
        TextView mdaysMonth = (TextView) customLayout.findViewById(R.id.days_month);
        TextView  cancel = (TextView) customLayout.findViewById(R.id.cancel_click_calendar);
        TextView okClick = (TextView) customLayout.findViewById(R.id.ok_click_calendar);


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
                    date_chosen = "Hier, "+date_choice;
                else if(i2==days)
                    date_chosen = "Aujourd'hui, "+date_choice;
                else if(i2==days+1)
                    date_chosen = "Demain, "+date_choice;
                else
                    date_chosen=daysMonth;


            }
        });
        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();


        okClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateBegin.setText(date_chosen);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateBegin.setText("");
                dialog.cancel();
            }
        });

        dialog.show();


    }

    private void showAlertHourInfoDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.activity_alert_hour_info, null);
        EditText  medTakeNumber = (EditText) customLayout.findViewById(R.id.number);
        Spinner types = (Spinner) customLayout.findViewById(R.id.spinner_type_med);
        TextView  cancel = (TextView) customLayout.findViewById(R.id.cancel_hour_alert);
        TextView define = (TextView) customLayout.findViewById(R.id.define_hour_alert);
        ImageButton increment = (ImageButton) customLayout.findViewById(R.id.increment_number);
        ImageButton decrement = (ImageButton) customLayout.findViewById(R.id.decrement_number);
        EditText hour = (EditText) customLayout.findViewById(R.id.hour);
        EditText minute = (EditText) customLayout.findViewById(R.id.minute);



        ArrayList<String> listTypeMed = new ArrayList<String>();
        listTypeMed.addAll(Arrays.asList(new String[]{"Pillule(s)", "Ampoule(s)", "Flacon(s)","mL","cartouches"}));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AddMedicationActivity.this, android.R.layout.simple_spinner_item,listTypeMed);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(myAdapter);


        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n = Double.parseDouble(medTakeNumber.getText().toString());
                n+=0.25;
                medTakeNumber.setText(String.valueOf(n), TextView.BufferType.EDITABLE);
                medicationTakeNumber = n;

            }
        });


        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n = Double.parseDouble(medTakeNumber.getText().toString());
                n-=0.25;
                medTakeNumber.setText(String.valueOf(n));

                if(n==0.25){
                    decrement.setEnabled(false);
                }

            }
        });
        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();

        define.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {


                medNumber.setText("Prendre "+medTakeNumber.getText().toString());
                alertHour.setText(hour.getText().toString()+" : "+minute.getText().toString());

                hourValue = Integer.parseInt(hour.getText().toString());
                minValue = Integer.parseInt(minute.getText().toString());
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void selectionDaysDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.days_dialog_layout, null);

        TextView  cancel = (TextView) customLayout.findViewById(R.id.cancel_day_selection);
        TextView define = (TextView) customLayout.findViewById(R.id.defiine_day_selection);
        ListView lvCheckDays =  customLayout.findViewById(R.id.listview_checkbox_days);

        String[]items=getResources().getStringArray(R.array.days);

        DaysSelectionAdapter daysSelectionAdapter=new DaysSelectionAdapter(this,R.layout.checkbox_days_layout,Arrays.asList(items));
        lvCheckDays.setAdapter(daysSelectionAdapter);



        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();

        String oldmsg = specificDay.getText().toString();

       define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                specificDay.setText("jours spécifiques de la semaine : "+ daysChosen);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public String getDays(String day){
        if(daysChosen.contains(day)){
            daysChosen = daysChosen.replace(day,"");

        }else{
            daysChosen = daysChosen+" "+day;
        }
        return daysChosen;
    }

    private void definePotencyConfig(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.activity_potency_config, null);
        EditText  potencyValue = (EditText) customLayout.findViewById(R.id.potency_value);
        Spinner units = (Spinner) customLayout.findViewById(R.id.spinner_potency_unit);
        TextView  cancel = (TextView) customLayout.findViewById(R.id.cancel_potency);
        TextView define = (TextView) customLayout.findViewById(R.id.define_potency);
        ImageButton increment = (ImageButton) customLayout.findViewById(R.id.increment_potency);
        ImageButton decrement = (ImageButton) customLayout.findViewById(R.id.decrement_potency);

        ArrayList<String> listUnité = new ArrayList<String>();
        listUnité.addAll(Arrays.asList(new String[]{"mg", "g"}));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AddMedicationActivity.this, android.R.layout.simple_spinner_item,listUnité);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(myAdapter);


        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();


        dialog.show();

    }


    private void saveData() {
        AppDatabase db = AppDatabase.getDataBase(getApplicationContext());

        PatientDao patientDao = db.patientDao();
        Patient pat = new Patient("prenom","nom","admin","monmdp");
        patientDao.insert(pat);
         int heure = hourValue;

         int minutes = minValue;

         int duree = 2;

         String jours = daysChosen;

         long idPatient = 1;
        ProgrammeDao programmeDao = db.programmeDao();
        Programme p = new Programme(heure,minutes,2,jours,1);
        long idPro = programmeDao.insert(p);

        List<Programme> list = new ArrayList<>();
        list = programmeDao.getAll();

        String nom = medName.getText().toString();

        CategorieMedicamentDao cat = db.categorieMedicamentDao();
        CategorieMedicament c = new CategorieMedicament("Pillule");
        cat.insert(c);

        MedicamentDao medicament = db.medicamentDao();
        Medicament m = new Medicament(nom,"",10,1);
        long idmed = medicament.insert(m);


        RappelDao rappelDao = db.rappelDao();
        Rappel rap = new Rappel();



         rap.setMedicamentId(idmed);
         rap.setProgrammeId(idPro);
         Programme pro = programmeDao.getById(idPro);
         Medicament med = medicament.getById(idmed);

         rap.setHeure(pro.getHeure());
         rap.setMinutes(pro.getMinutes());
         rap.setQtePilule(medicationTakeNumber);
         rap.setMessage("Prendre "+rap.getQtePilule()+med.getCategorieId());

         rappelDao.insert(rap);

         List<Rappel> rappels = rappelDao.getAll();

        Log.d(TAG,"run:"+list.toString());
        Log.d(TAG,"run:"+rappels.toString());



    }

    private void checkLogin(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREF_NAME,MODE_PRIVATE);
                boolean hasLoggedIn = sharedPreferences.getBoolean("hasLoggedIn",false);
                if(hasLoggedIn){
                    Intent intent = new Intent(AddMedicationActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(AddMedicationActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
    }


}