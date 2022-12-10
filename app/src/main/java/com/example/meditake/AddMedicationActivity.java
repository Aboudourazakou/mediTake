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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class AddMedicationActivity extends AppCompatActivity {

    LinearLayout partie2,buttons ,linearLayoutDate , alertHourInfo , potencyInfo , medWithAlert , medWithoutAlert;
    Button btnNext , btnNext2 , btnBarSave,btnOtherOptions ,btnSaveFirst ,save;
    TextView dateBegin,medNumber,alertHour,potencyConfig ;
    RadioButton daysNumber,specificDay , allDays, beforeMeal,afterMeal,duringMeal,anyway;
    RadioGroup foodInstructions,rdGroupDuration;
    Switch switchAlert;
    CardView programForm;

    Spinner spnFrequence;
    EditText medName;
    ImageButton btnClose ;

    LinearLayout addTitle , otherOptionView;
    CardView medIcons;
    String date_chosen;
    Medicament medicamentSelectione = null;
    int hourValue,minValue;
    double medicationTakeNumber;
    String daysChosen;
    double medPotency;
    String instructions;
    int duration;


    /* Configuration de la recherche de medicament */
    ListView medicamentListview;
    AppDatabase db = null;
    Medicament medicamentSelectione = null;
    // Fin config

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
        switchAlert =  findViewById(R.id.switch_hour_alert);
        medWithAlert = findViewById(R.id.med_with_alert);
        medWithoutAlert = findViewById(R.id.med_without_alert);
        programForm = findViewById(R.id.program_form);

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
        rdGroupDuration = findViewById(R.id.rd_group_duration);
        specificDay = findViewById(R.id.specific_days);
        allDays = findViewById(R.id.all_days);
        medNumber = findViewById(R.id.number_medication);
        save = findViewById(R.id.btn_save_final);
        daysNumber = findViewById(R.id.days_number);
        potencyConfig = findViewById(R.id.potencyconfig_click);
        foodInstructions = findViewById(R.id.food_instruction);
        afterMeal = findViewById(R.id.after_food);
        beforeMeal = findViewById(R.id.before_food);
        duringMeal = findViewById(R.id.during_food);
        anyway = findViewById(R.id.anyway);

        hourValue=0;
        minValue=0;
        daysChosen="";
        medicationTakeNumber=0.0;
        duration = 0;
        instructions="";

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

                    medicamentListview.setVisibility(View.VISIBLE);

                    medicamentPropositionList.clear();
                    List<Medicament> medicaments = medicamentDao.getAll();
                    medicamentPropositionList.addAll(medicaments.stream()
                            .filter(m->m.getNom().toLowerCase().contains(medName.getText().toString().trim().toLowerCase()))
                            .collect(Collectors.toList()));

                    medicamentPropositionListviewAdapter.notifyDataSetChanged();


                    medicamentListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            medicamentSelectione = (Medicament) medicamentPropositionListviewAdapter.getItem(i);
                            medName.setText(medicamentSelectione.getNom());

                            medicamentListview.setVisibility(View.INVISIBLE);
                        }
                    });

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

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat formater = new SimpleDateFormat("d MMM.");
        dateBegin.setText("Aujourd'hui, "+formater.format(currentTime));

        linearLayoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendarAlertDialog();
            }
        });

        switchAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){

                    medWithAlert.setVisibility(View.GONE);
                    medWithoutAlert.setVisibility(View.VISIBLE);
                    programForm.setVisibility(View.GONE);
                } else{

                    medWithAlert.setVisibility(View.VISIBLE);
                    medWithoutAlert.setVisibility(View.GONE);
                    programForm.setVisibility(View.VISIBLE);
                }
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







        alertHourInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertHourInfoDialog();
            }
        });

      /*  switch(rdGroupDuration.getCheckedRadioButtonId()){
            case R.id.duration_no_specified:
                duration = 0;
                break;
            case R.id.days_number:

                break;
        }*/
        daysNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defineDuration();
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


        potencyConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                definePotencyConfig();
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

        btnSaveFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        saveData();

                    }

                }).start();
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

        foodInstructions();

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
        SimpleDateFormat forme = new SimpleDateFormat("yyyy");
        myear.setText(""+ forme.format(current));
        SimpleDateFormat form = new SimpleDateFormat("EEE. dd MMM.");
        String dm= form.format(current);
        mdaysMonth.setText(dm);


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

        //String text = types.getSelectedItem().toString();

        medicationTakeNumber = Double.parseDouble(medTakeNumber.getText().toString());
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
                medicationTakeNumber = n;
                if(n==0.25){
                    decrement.setEnabled(false);
                }

            }
        });
        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();

        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                medNumber.setText("Prendre "+medTakeNumber.getText().toString()+" "+types.getSelectedItem().toString());
                alertHour.setText(hour.getText().toString()+" : "+minute.getText().toString());

                hourValue = Integer.parseInt(hour.getText().toString());
                minValue = Integer.parseInt(minute.getText().toString());

                getMessageInstructions(medNumber.getText().toString() + ", "+spnFrequence.getSelectedItem().toString()+", ");
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


        dialog.show();


    }

    private void defineDuration(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddMedicationActivity.this);
        View customLayout = getLayoutInflater().inflate(R.layout.duration_layout, null);
        EditText  durationValue = (EditText) customLayout.findViewById(R.id.duration_value);
        Button  cancel =  customLayout.findViewById(R.id.cancel_potency);
        Button define = (Button) customLayout.findViewById(R.id.define_potency);
        ImageButton increment = (ImageButton) customLayout.findViewById(R.id.increment_duration);
        ImageButton decrement = (ImageButton) customLayout.findViewById(R.id.decrement_duration);


        duration = Integer.parseInt(durationValue.getText().toString());
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d = Integer.parseInt(durationValue.getText().toString());
                d=d+1;
                durationValue.setText(String.valueOf(d));
                //  duration = d;

            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int d = Integer.parseInt(durationValue.getText().toString());
                d=d-1;
                durationValue.setText(String.valueOf(d));
                //  duration = d;
            }
        });
        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();

        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                duration = Integer.parseInt(durationValue.getText().toString());
                daysNumber.setText("nombre de jours : "+duration);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
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

        //  String oldmsg = specificDay.getText().toString();

        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                specificDay.setText("jours spécifiques de la semaine : "+ daysChosen);
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specificDay.setText("jours spécifiques de la semaine ");
                dialog.cancel();
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
        Button  cancel =  customLayout.findViewById(R.id.cancel_potency);
        Button define =  customLayout.findViewById(R.id.define_potency);
        ImageButton increment = (ImageButton) customLayout.findViewById(R.id.increment_potency);
        ImageButton decrement = (ImageButton) customLayout.findViewById(R.id.decrement_potency);

        ArrayList<String> listUnité = new ArrayList<String>();
        listUnité.addAll(Arrays.asList(new String[]{"mg", "g","mL","mg/g","mcg","Ul"}));
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AddMedicationActivity.this, android.R.layout.simple_spinner_item,listUnité);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        units.setAdapter(myAdapter);

        medPotency = Double.parseDouble(potencyValue.getText().toString());

        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double p = Double.parseDouble(potencyValue.getText().toString());
                p=p+1;
                potencyValue.setText(String.valueOf(p));
                //  medPotency = p;

            }
        });


        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double p = Double.parseDouble(potencyValue.getText().toString());
                p=p-1;
                potencyValue.setText(String.valueOf(p));
                // medPotency = p;
            }
        });
        alertDialog.setView(customLayout);
        AlertDialog dialog = alertDialog.create();

        define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s="";
                String text = units.getSelectedItem().toString();
                medPotency = Double.parseDouble(potencyValue.getText().toString());
                s=medPotency+""+text;
                potencyConfig.setText(s);
                getMessageInstructions("de puissance "+potencyConfig.getText().toString());
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });


        dialog.show();

    }

    private void foodInstructions(){
        String msg="";
        switch(foodInstructions.getCheckedRadioButtonId()){
            case R.id.before_food:
                msg = beforeMeal.getText().toString();
                break;
            case R.id.during_food:
                msg = duringMeal.getText().toString();
                break;
            case R.id.after_food:
                msg = afterMeal.getText().toString();
                break;
            case R.id.anyway:
                msg = anyway.getText().toString() + "le moment du repas";
                break;
        }
        EditText otherIns = findViewById(R.id.other_instruction);
        String ins = "";
        ins=otherIns.getText().toString();
        msg = msg+" "+ins;

        getMessageInstructions(msg);
    }

    public String getMessageInstructions(String msg){
        instructions = instructions +" "+msg;
        return instructions;
    }


    private void saveData() {
        AppDatabase db = AppDatabase.getDataBase(getApplicationContext());

        int heure = hourValue;

        int minutes = minValue;

        int duree = duration;

        String jours = daysChosen;

        ProgrammeDao programmeDao = db.programmeDao();
        Programme p = new Programme(heure,minutes,duree,jours,1);
        long idPro = programmeDao.insert(p);

        List<Programme> list = programmeDao.getAll();


        RappelDao rappelDao = db.rappelDao();
        Rappel rap = new Rappel();

        rap.setMedicamentId(medicamentSelectione.getId());
        rap.setProgrammeId(idPro);
        Programme pro = programmeDao.getById(idPro);

        rap.setHeure(pro.getHeure());
        rap.setMinutes(pro.getMinutes());
        rap.setQtePilule(medicationTakeNumber);
        rap.setMessage(instructions);

        rappelDao.insert(rap);

        List<Rappel> rappels = rappelDao.getAll();

        Log.d(TAG,"run:"+list.toString());
        Log.d(TAG,"run:"+rappels.toString());



    }

}