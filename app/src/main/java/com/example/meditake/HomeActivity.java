package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.meditake.adapters.DaysAdapter;
import com.example.meditake.adapters.MenuItemAdapter;
import com.example.meditake.adapters.RappelAdapter;
import com.example.meditake.alarm.AlarmReceiver;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.databinding.ActivityHomeBinding;
import com.example.meditake.models.MenuItem;
import com.example.meditake.models.Rappel;
import com.example.meditake.viewmodels.HomeActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

public class HomeActivity extends AppCompatActivity {
    List<String> daysList=new ArrayList<>();
    ActivityHomeBinding binding;
    List<Rappel>rappelList=new ArrayList<>();
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    HomeActivityViewModel homeActivityViewModel;
    List<Programme>programmeList=new ArrayList<>();
    Timer timer=new Timer();
    String today;
    int counter=0;
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
        Boolean isWeekDays=true;
        binding.daysListView.setLayoutManager(new GridLayoutManager(this,7));
        binding.daysListView.setAdapter(new DaysAdapter(daysList,this,isWeekDays));
        isWeekDays=false;
        binding.dayNumberListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.dayNumberListView.setAdapter(new DaysAdapter(totalDays,this,isWeekDays));

        for (int i = 0; i < 4; i++) {
            Rappel rappel=new Rappel();
            rappelList.add(rappel);
        }

        binding.rappelsList.setLayoutManager(new LinearLayoutManager(this));
        binding.rappelsList.setAdapter(new RappelAdapter(rappelList,this));

//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("Ca commence de belle");
//                if(totalDays.get(counter).equals(today)){
//                    timer.cancel();
//                    timer=null;
//                }else {
//
//                counter++;
//                }
//            }
//        },1,1);
        binding.dayNumberListView.smoothScrollToPosition(35);

        createNotificationChannel();
        createAlarm();

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

}