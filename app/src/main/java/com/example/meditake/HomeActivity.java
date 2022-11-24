package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
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
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.meditake.adapters.DaysAdapter;
import com.example.meditake.adapters.ProgrammeAdapter;
import com.example.meditake.alarm.AlarmReceiver;
import com.example.meditake.databinding.RappelClickDialogBinding;
import com.example.meditake.databinding.RappelsListWrapperBinding;
import com.example.meditake.models.Medicament;
import com.example.meditake.models.Programm;
import com.example.meditake.databinding.ActivityHomeBinding;
import com.example.meditake.models.Rappel;
import com.example.meditake.viewmodels.HomeActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
    Dialog dialog;

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
        rappel.setStatut("pas encore");
        rappel.setQtePilule(8);
        rappel.setMessage("Prendre "+rappel.getQtePilule()+" pilulles");
        rappel.setHeure(13);
        rappel.setMinutes(12);
        Rappel rappel1=new Rappel();
        rappel1.setStatut("pas encore");
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


        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
         RappelClickDialogBinding dialogBinding= DataBindingUtil.inflate(layoutInflater,R.layout.rappel_click_dialog,null,false);
         dialogBinding.setRappel(rappel);
        System.out.println(rappel.getMedicament().getNom()+"ocrmed");

        dialog = new Dialog(HomeActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.ignorePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialogBinding.takePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        dialogBinding.reschedulePillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialog.show();
    }



}