package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.RappelWithRapportAndMedicament;

import java.util.List;

@Dao
public interface RappelDao {
    @Query("Select * from Rappel")
    List<Rappel> getAll();


    @Insert
    long insert(Rappel p);

    @Query("Select * from Rappel where id = :id")
    Rappel getById(int id);

    @Query("SELECT * FROM Rappel WHERE id IN (:userIds)")
    List<Rappel> loadAllByIds(int[] userIds);

    @Transaction
    @Query("select * from rappel where id= :id")
    RappelWithRapportAndMedicament getRappel(long id);

    @Insert
    void insertAll(Rappel ...rappels);

    @Delete
    void delete(Rappel rappel);
}
