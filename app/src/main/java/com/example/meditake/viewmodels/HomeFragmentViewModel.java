package com.example.meditake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.meditake.HomeActivity;
import com.example.meditake.HomeFragment;
import com.example.meditake.database.AppDatabase;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.RapportDao;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/***
 "Created by  TETEREOU Aboudourazakou on "12/1/2022
 "Project name "MediTake
 */
public class HomeFragmentViewModel  extends  HomeActivityViewModel{
    AppDatabase db;
    private List<Programme> programmeList=new ArrayList<>();
    HomeFragment homeFragment;
    private MutableLiveData<List<Programme>> programLiveData;

    public LiveData<List<Programme>> getAllPrograms(){
        if(programLiveData==null)programLiveData=new MutableLiveData<>();
        if(programmeList.size()<=0) getProgramsFromRoomDb();
        return  programLiveData;
    }
    public void getProgramsFromRoomDb() {

        AppDatabase db = AppDatabase.getDataBase(homeFragment.getActivity().getApplicationContext());
        ProgrammeDao programmeDao=db.programmeDao();
        List<Programme>programmes=programmeDao.getAll();

        List<Programme>dayProgramList=new ArrayList<>();
        for (int i = 0; i <programmes.size() ; i++) {

            Programme programme=  programmes.get(i);

            if(programme.getJours().contains(homeFragment.getSelectedDay())){
                dayProgramList.add(programme);
                RappelDao rappelDao=db.rappelDao();
                List<Rappel>rappelList=rappelDao.findRappelByIdProgram(programme.getId());
                for (int j = 0; j <rappelList.size() ; j++) {

                    RapportDao rapportDao=db.rapportDao();
                    Rappel rappel=rappelList.get(j);
                    MedicamentDao medicamentDao=db.medicamentDao();
                    rappel.setMedicament(medicamentDao.getById(rappel.getMedicamentId()));
                    List< Rapport >rapports=rapportDao.findRapportByIdRappel(rappel.getId());
                    rappel.setRapportList(rapports);

                  if(!isAlarmExpired(rappel.getHeure(),rappel.getMinutes())){
                      homeFragment.createAlarm(rappel.getHeure(),rappel.getMinutes(),j+1);
                  }



                }
                programme.setRappelList(rappelList);
               if(!isAlarmExpired(programme.getHeure(),programme.getMinutes())){
                   homeFragment.createAlarm(programme.getHeure(),programme.getMinutes(),i+100);
               }

            }

            programmeList=dayProgramList;
            programLiveData.postValue(programmeList);


        }


    }

    public  void setHomeFragment(HomeFragment homeFragment){
        this.homeFragment=homeFragment;
        db=AppDatabase.getDataBase(homeFragment.getActivity().getApplicationContext());
    }

    public  void  saveRapport(Rapport rapport){
        RapportDao rapportDao=db.rapportDao();
        rapportDao.insert(rapport);
        getProgramsFromRoomDb();
    }

    public  void updateRappel(Rappel rappel){
        RappelDao rappelDao=db.rappelDao();
        rappelDao.update(rappel);
    }

    public  void updateMedicament(Medicament medicament){
        MedicamentDao medicamentDao=db.medicamentDao();
        medicamentDao.update(medicament);
        getProgramsFromRoomDb();

    }


    public  boolean isAlarmExpired(int hour,int minutes){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minutes);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long now=new Date().getTime();
        long alarmTime=calendar.getTimeInMillis();
        if(now<=alarmTime){
              return  false;
        }
        return  true;
    }

    public void getUpdateMissedPills() {

        AppDatabase db = AppDatabase.getDataBase(homeFragment.getContext().getApplicationContext());
        RappelDao rappelDao=db.rappelDao();
        RapportDao rapportDao=db.rapportDao();
        List<Rappel>rappelList=rappelDao.getAll();
        for(Rappel rappel:rappelList){
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,rappel.getHeure());
            calendar.set(Calendar.MINUTE,rappel.getMinutes());
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            if(calendar.getTimeInMillis()<new Date().getTime()- TimeUnit.MINUTES.toMillis(10) ){
                List<Rapport>rapportList=rapportDao.findRapportByIdRappel(rappel.getId());
                boolean isTaken=false;
                for (Rapport rapport:rapportList){
                    if(rapport.getStatut().equals("pris")){
                        isTaken=true;
                        break;
                    }


                }
                if(!isTaken){
                    Rapport rapport=new Rapport();
                    rapport.setIdRappel(rappel.getId());
                    rapport.setDate(new Date().getTime());
                    rapport.setMessage("Vous avez manque ce medicament");
                    rapport.setStatut("manque");
                    rapportDao.insert(rapport);
                }

            };
        }

    }

    public  void  deleteProgramme(Long id){
          AppDatabase db = AppDatabase.getDataBase(homeFragment.getContext().getApplicationContext());
          ProgrammeDao programmeDao=db.programmeDao();
          Programme programme=programmeDao.getById(id);
          RapportDao rapportDao=db.rapportDao();
          RappelDao rappelDao=db.rappelDao();
          List<Rappel>rappelList=db.rappelDao().findRappelByIdProgram(id);
          for(Rappel rappel:rappelList){
               List<Rapport>rapportList=rapportDao.findRapportByIdRappel(rappel.getId());
               for(Rapport rapport:rapportList){
                   rapportDao.delete(rapport);
               }
               rappelDao.delete(rappel);
          }
          programmeDao.delete(programme);
    }


}
