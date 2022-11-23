package com.example.meditake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.meditake.HomeActivity;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.entities.Programme;

import java.util.ArrayList;
import java.util.List;

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
        if(programmeList.size()<=0) getProgramsFromRoomDb();
        return  programLiveData;
    }

    private void getProgramsFromRoomDb() {

        AppDatabase db = AppDatabase.getDataBase(context.getApplicationContext());
        ProgrammeDao programmeDao=db.programmeDao();
        programmeList.addAll( programmeDao.getAll());
        programLiveData.postValue(programmeList);

    }

    public  void setContext(HomeActivity activity){
        this.context=activity;
    }
}
