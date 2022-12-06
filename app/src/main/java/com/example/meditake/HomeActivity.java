package com.example.meditake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.view.MenuItem;
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

import com.example.meditake.databinding.ActivityHomeBinding;

import com.example.meditake.viewmodels.HomeActivityViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    //List<String> daysList=new ArrayList<>();
    ActivityHomeBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.navigation_medicament:replaceFragment(new AddMedicamentFragment());
                    break;
                }
                return  true;
            }
        });

    }

    public  void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }



}