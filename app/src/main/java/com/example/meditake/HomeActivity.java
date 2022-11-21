package com.example.meditake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.meditake.adapters.DaysAdapter;
import com.example.meditake.adapters.MenuItemAdapter;
import com.example.meditake.databinding.ActivityHomeBinding;
import com.example.meditake.models.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

public class HomeActivity extends AppCompatActivity {
    List<String> daysList=new ArrayList<>();
    ActivityHomeBinding binding;
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
        binding.dayNumberListView.smoothScrollBy(2520,1500);


    }
}