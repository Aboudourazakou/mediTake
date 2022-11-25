package com.example.meditake.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.meditake.database.dao.JournalDao;
import com.example.meditake.database.dao.MedecinDao;
import com.example.meditake.database.dao.MedicamentDao;
import com.example.meditake.database.dao.PatientDao;
import com.example.meditake.database.dao.ProgrammeDao;
import com.example.meditake.database.dao.ProgrammeWithRappelDao;
import com.example.meditake.database.dao.RappelDao;
import com.example.meditake.database.dao.TypeMedicamentDao;
import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Journal;
import com.example.meditake.database.entities.Medecin;
import com.example.meditake.database.entities.Medicament;
import com.example.meditake.database.entities.Patient;
import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.Rapport;

@Database(entities = {Medecin.class, Programme.class,
        Patient.class, Journal.class, Medicament.class,
        Rappel.class, CategorieMedicament.class, Rapport.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedecinDao medecinDao();
    public abstract PatientDao patientDao();
    public abstract MedicamentDao medicamentDao();
    public abstract TypeMedicamentDao typeMedicamentDao();
    public abstract ProgrammeDao programmeDao();
    public abstract RappelDao rappelDao();
    public abstract JournalDao journalDao();
    public abstract ProgrammeWithRappelDao programmeWithRappelDao();

    private static AppDatabase appDatabase;

    synchronized
    public static AppDatabase getDataBase(Context context){
        if (null == appDatabase) {

            appDatabase = Room.databaseBuilder(context, AppDatabase.class,"app_database").fallbackToDestructiveMigration().allowMainThreadQueries().
                    build();
        }
        return appDatabase;
    }


}
