package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.meditake.database.entities.Medicament;

import java.util.Collection;
import java.util.List;

@Dao
public interface MedicamentDao {
     @Query("Select * from Medicament")
     List<com.example.meditake.database.entities.Medicament> getAll();

    @Query("Select * from Medicament where id = :id")
    com.example.meditake.database.entities.Medicament getById(long id);

    @Query("SELECT * FROM Medicament WHERE id IN (:userIds)")
    List<com.example.meditake.database.entities.Medicament> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(com.example.meditake.database.entities.Medicament...medicaments);

    @Insert
    long insert(com.example.meditake.database.entities.Medicament medicament);

    @Delete
    void delete(com.example.meditake.database.entities.Medicament medicament);

    @Update
    void update(com.example.meditake.database.entities.Medicament medicament);

    @Query("select * from medicament where nom LIKE '%'+:nom+'%'")
    List<Medicament> getByNom(String nom);
}
