package com.example.meditake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    CalendarView mCalendarView;

    TextView myear , mdaysMonth , texte;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = findViewById(R.id.calendar_id);
        myear = findViewById(R.id.txtview_year);
        mdaysMonth = findViewById(R.id.days_month);
        texte = findViewById(R.id.text_view);

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
    }
}