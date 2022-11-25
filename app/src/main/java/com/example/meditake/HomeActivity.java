package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.meditake.adapters.DaysAdapter;
import com.example.meditake.adapters.IgnoreReasonAdapter;
import com.example.meditake.adapters.MenuItemAdapter;
import com.example.meditake.adapters.ProgrammeAdapter;
import com.example.meditake.alarm.AlarmReceiver;
import com.example.meditake.databinding.IgnoreMessageDialogBinding;
import com.example.meditake.databinding.RappelClickDialogBinding;
import com.example.meditake.databinding.RappelsListWrapperBinding;
import com.example.meditake.databinding.RescheduleReminderDialogBinding;
import com.example.meditake.models.Medicament;
import com.example.meditake.models.Programm;
import com.example.meditake.databinding.ActivityHomeBinding;
import com.example.meditake.models.Rappel;
import com.example.meditake.models.Rapport;
import com.example.meditake.viewmodels.HomeActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    List<String> daysList=new ArrayList<>();
    ActivityHomeBinding binding;
     List<Programm> programmList =new ArrayList<>();
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    HomeActivityViewModel homeActivityViewModel;
    String today;
    Dialog dialog,dialog2;
    Rappel rappel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        String[]days=getResources().getStringArray(R.array.days);
        LinkedHashMap<String,String>daysAfter=new LinkedHashMap();
        LinkedHashMap<String,String> daysBefore=new LinkedHashMap();
        Calendar calendar=Calendar.getInstance();
        Calendar calendar1=Calendar.getInstance();
        for (int i = 0; i <30 ; i++) {
            calendar.add(Calendar.DATE,i);
           String d= new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(calendar.getTime());
            daysAfter.put(d,"ici");
            if(i==0)today=d;

            calendar.add(Calendar.DATE,-1*i);

        }
        for (int i = 1; i <=30 ; i++) {
            calendar1.add(Calendar.DATE,-1*i);
            String d= new SimpleDateFormat("EEE d MMM", Locale.FRANCE).format(calendar1.getTime());
            daysBefore.put(d,"kdkd");
            calendar1.add(Calendar.DATE,i);

        }

        System.out.println("Jour j");
        List<String>arrl=new ArrayList<String>(daysBefore.keySet());
        for (String s:arrl
             ) {
            System.out.println(s);
        }





        for(int i=0;i<days.length;i++){

            daysList.add(days[i]);
        }
        List<String>totalDays=new ArrayList<>(daysBefore.keySet());
        Collections.reverse(totalDays);
        totalDays.addAll(daysAfter.keySet());
        simulateDb(daysAfter.get(today));
        Boolean isWeekDays=true;
        binding.daysListView.setLayoutManager(new GridLayoutManager(this,7));
        binding.daysListView.setAdapter(new DaysAdapter(daysList,this,isWeekDays));
        isWeekDays=false;
        binding.dayNumberListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.dayNumberListView.setAdapter(new DaysAdapter(totalDays,this,isWeekDays));


        binding.rappelsList.setLayoutManager(new LinearLayoutManager(this));


        createNotificationChannel();
        //createAlarm();

       /* homeActivityViewModel=new ViewModelProvider(this).get(HomeActivityViewModel.class);
        homeActivityViewModel.setContext(this);
        homeActivityViewModel.getAllPrograms().observe(this, new Observer<List<Programme>>() {
            @Override
            public void onChanged(List<Programme> programmes) {
                   programmeList=programmes;
            }
        });*/



    }

    private void createAlarm() {
        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,00);
        calendar.set(Calendar.MINUTE,14);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence sequence="TakingMedicamentChannel";
            String description="Ce canal cree les notifications";
            int importance= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("takemedicament",sequence,importance);
            channel.setDescription(description);
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }
    private void cancelAlarm(){
        Intent intent=new Intent(this,AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
        if(alarmManager==null){
            alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);

    }

    public  ActivityHomeBinding getBinding(){
        return  binding;
    }


    public  void simulateDb(String day){
        programmList.clear();
        Programm programm =new Programm();
        programm.setHeure(6);
        programm.setJours("lun. mar. mer. jeu. ven.");
        programm.setMinutes(45);
        Rappel rappel=new Rappel();
        rappel.setId(1);
        rappel.setQtePilule(8);
        rappel.setMessage("Prendre "+rappel.getQtePilule()+" pilulles");
        rappel.setHeure(13);
        rappel.setMinutes(12);
        Rappel rappel1=new Rappel();
        rappel1.setId(2);
        rappel1.setQtePilule(9);
        rappel1.setMessage("Prendre "+rappel.getQtePilule()+" pilulles");
        rappel1.setHeure(13);
        rappel1.setMinutes(12);
        Medicament medicament=new Medicament();
        medicament.setNom("Peniciline");
        medicament.setQte(67);
        Medicament medicament1=new Medicament();
        medicament1.setNom("Paracetamol");
        medicament1.setQte(9);
        rappel1.setMedicament(medicament1);
        rappel.setMedicament(medicament);
        List<Rappel> rappels=new ArrayList<>();
        rappels.add(rappel);
        rappels.add(rappel1);
        programm.setRappel(rappels);
        this.programmList.add(programm);


        //Remove previous list
         binding.rappelsList.setAdapter(new ProgrammeAdapter(new ArrayList<>(),this));
         binding.dayNumberListView.smoothScrollToPosition(35);
        //Set new list
        if(programm.getJours().contains(day)){

            binding.rappelsList.setAdapter(new ProgrammeAdapter(programmList,this));
            binding.dayNumberListView.smoothScrollToPosition(35);
        }


    }

    public  void prendreMedicament(Rappel rappel){
             showDialog(rappel);
    }
    public  void showDialog(Rappel rappel){

        this.rappel=rappel;
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
         RappelClickDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.rappel_click_dialog,null,false);
         dialogBinding.setRappel(rappel);
        System.out.println(rappel.getMedicament().getNom()+"ocrmed");

        this.dialog = new Dialog(HomeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        this.dialog.setCancelable(true);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        this.dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.ignorePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rapport rapport=new Rapport();
                rapport.setDate(new Date().getTime());
                rapport.setStatut("ignore");
                rapport.setMessage("Medicament est termine");
                rappel.getRapportList().add(rapport);
                showDialogForIgnoreMessage(rappel);
                updateDb(rappel);
            }
        });
        dialogBinding.takePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rapport rapport=new Rapport();
                rapport.setDate(new Date().getTime());
                rapport.setStatut("pris");
                rapport.setMessage("Le pilule a ete pris le "+new SimpleDateFormat("EEE d MMM h:mm a", Locale.FRANCE).format(Calendar.getInstance().getTime()));
                rappel.getRapportList().add(rapport);
                updateDb(rappel);
                dialog.cancel();
            }
        });
        dialogBinding.reschedulePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogForReminderRescheduling(rappel);

            }
        });

        dialog.show();
    }

    public  void updateDb(Rappel rappel){

        binding.rappelsList.setAdapter(new ProgrammeAdapter(programmList,this));
        binding.dayNumberListView.smoothScrollToPosition(35);
        this.dialog.cancel();

    }


    public  void  showDialogForIgnoreMessage(Rappel rappel){
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
        IgnoreMessageDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.ignore_message_dialog,null,false);

       Dialog dialog2 = new Dialog(HomeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        String[]items=getResources().getStringArray(R.array.reasons);

        IgnoreReasonAdapter ignoreReasonAdapter=new IgnoreReasonAdapter(this,R.layout.reasons_radio_element,Arrays.asList(items));
         dialogBinding.reasonListView.setAdapter(ignoreReasonAdapter);


        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        dialog2.setContentView(dialogBinding.getRoot());
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        this.dialog2=dialog2;
        dialog2.show();


    }

    public  void setReasonOfIgnore(String reason){
        Rapport rapport=createRapport();
        rappel.getRapportList().add(rapport);
        rapport.setStatut("ignore");
        rapport.setMessage(reason);
        dialog2.cancel();
        updateDb(this.rappel);
    }
    public  void   showDialogForReminderRescheduling(Rappel rappel){
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
        RescheduleReminderDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.reschedule_reminder_dialog,null,false);

       Dialog dialog2 = new Dialog(HomeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        dialog2.setContentView(dialogBinding.getRoot());
        Window window = dialog2.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog2.show();
        EditText editText1=dialogBinding.codeInputEdittext1;
        EditText editText2=dialogBinding.codeInputEdittext2;
        EditText editText3=dialogBinding.codeInputEdittext3;
        EditText editText4=dialogBinding.codeInputEdittext4;
        Context context=getApplicationContext();

        editText1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText1.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText1.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    editText2.requestFocus();
                }
                else {



                    editText1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText1.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (editText2.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText2.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    editText3.requestFocus();
                }
                else {

                    editText2.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText1.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText3.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText3.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    editText4.requestFocus();
                }
                else {
                    editText3.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText2.requestFocus();

                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        editText4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText4.getText().toString().length() == 1)     //size as per your requirement
                {

                    editText4.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

                }
                else {
                    editText4.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.line));
                    editText3.requestFocus();

                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

           dialogBinding.reschedulePillBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int h1=Integer.parseInt(editText1.getText().toString());
                   int h2=Integer.parseInt(editText2.getText().toString());
                   int m1=Integer.parseInt(editText3.getText().toString());
                   int m2=Integer.parseInt(editText4.getText().toString());

                   Rapport rapport=createRapport();
                   rappel.setHeure(h1*10+h2);
                   rappel.setMinutes(m1*10+m2);
                   rappel.getRapportList().add(rapport);
                   rapport.setStatut("reprogramme");
                   rapport.setMessage("La pilule a ete reprogramme pour " +rappel.getHeure()+":"+rappel.getMinutes());
                   dialog2.cancel();
                   updateDb(rappel);


               }
           });

    }

    public  Rapport createRapport(){
        Rapport rapport=new Rapport();
        rapport.setDate(new Date().getTime());

         return  rapport;
    }



}