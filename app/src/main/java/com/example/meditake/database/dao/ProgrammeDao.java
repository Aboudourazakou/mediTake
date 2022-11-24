package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;import com.example.meditake.database.entities.Programme;

import java.util.List;

@Dao
public interface ProgrammeDao {
    @Query("Select * from Programme")
    List<Programme> getAll();

    @Insert
    long insert(Programme p);

    @Query("Select * from Programme where id = :id")
    Programme getById(int id);

    @Query("SELECT * FROM Programme WHERE id IN (:userIds)")
    List<Programme> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Programme ...programmes);

    @Delete
    void delete(Programme programme);
}
