package com.example.meditake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.meditake.HomeActivity;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.RapportDao;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 "Created by  TETEREOU Aboudourazakou on "11/22/2022
 "Project name "MediTake
 */
public class HomeActivityViewModel  extends ViewModel {
    private List<Programme> programmeList=new ArrayList<>();
    private HomeActivity context;
    private MutableLiveData<List<Programme>>programLiveData;

    public LiveData<List<Programme>>getAllPrograms(){
        if(programLiveData==null)programLiveData=new MutableLiveData<>();

        return  programLiveData;
    }



    public  void setContext(HomeActivity activity){
        this.context=activity;
    }
}
