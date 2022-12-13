package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.Rappel;
import com.example.meditake.database.entities.RappelWithRapportAndMedicament;
import com.example.meditake.database.entities.Rapport;

import java.util.List;

@Dao
public interface RappelDao {
    @Query("Select * from Rappel")
    List<Rappel> getAll();

    @Query("select * from rappel where programmeId = :id")
    List<Rappel> findRappelByIdProgram(long id);

    @Insert
    long insert(Rappel p);

    @Query("Select * from Rappel where id = :id")
    Rappel getById(long id);

    @Query("SELECT * FROM Rappel WHERE id IN (:userIds)")
    List<Rappel> loadAllByIds(int[] userIds);

    @Transaction
    @Query("select * from rappel where id= :id")
    RappelWithRapportAndMedicament getRappel(long id);

    @Insert
    void insertAll(Rappel ...rappels);

    @Delete
    void delete(Rappel rappel);

    @Update
    void update(Rappel rappel);
    @Query("DELETE FROM Rappel")
    void deleteAll();
}
