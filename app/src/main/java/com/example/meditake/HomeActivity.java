package com.example.meditake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.meditake.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    //List<String> daysList=new ArrayList<>();
    ActivityHomeBinding binding;
    public  static  HomeActivity homeActivity;
    public  static long selectedMedicamentId=-1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        homeActivity=this;
        replaceFragment(new HomeFragment());
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.navigation_medicament:replaceFragment(new AddPill());
                    break;
                    case R.id.navigation_alarme_set:replaceFragment(new AddMedicamentFragmentToRappel());
                    break;
                    case R.id.navigation_journal:replaceFragment(new JournalFragment());
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