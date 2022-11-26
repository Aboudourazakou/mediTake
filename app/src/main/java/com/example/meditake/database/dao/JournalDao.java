package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.meditake.database.entities.Journal;

import java.util.List;

@Dao
public interface JournalDao {
   @Query("Select * from Journal")
    List<Journal> getAll();

    @Query("Select * from Journal where id = :id")
    Journal getById(long id);

    @Query("SELECT * FROM Journal WHERE id IN (:userIds)")
    List<Journal> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(Journal ...journals);

    @Insert
    void insert(Journal journal);

    @Delete
    void delete(Journal journal);
}
