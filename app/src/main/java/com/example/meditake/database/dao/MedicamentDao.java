package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.meditake.database.entities.Medicament;

import java.util.List;

@Dao
public interface MedicamentDao {
     @Query("Select * from Medicament")
     List<Medicament> getAll();

    @Query("Select * from Medicament where id = :id")
    Medicament getById(int id);

    @Query("SELECT * FROM Medicament WHERE id IN (:userIds)")
    List<Medicament> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Medicament ...medicaments);

    @Delete
    void delete(Medicament medicament);
}
