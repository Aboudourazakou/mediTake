package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;import com.example.meditake.database.entities.Rappel;

import java.util.List;

@Dao
public interface RappelDao {
    @Query("Select * from Rappel")
    List<Rappel> getAll();

    @Query("Select * from Rappel where id = :id")
    Rappel getById(int id);

    @Query("SELECT * FROM Rappel WHERE id IN (:userIds)")
    List<Rappel> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(Rappel ...rappels);

    @Delete
    void delete(Rappel rappel);
}
