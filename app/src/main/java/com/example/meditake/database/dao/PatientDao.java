package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.meditake.database.entities.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("Select * from Patient")
    List<Patient> getAll();

    @Query("Select * from Patient where id = :id")
    Patient getById(int id);

    @Query("SELECT * FROM Patient WHERE id IN (:userIds)")
    List<Patient> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Patient WHERE nom LIKE :first or " +
            "prenom LIKE :last LIMIT 1")
    Patient findByName(String first, String last);

    @Insert
    void insertAll(Patient ...patients);

    @Delete
    void delete(Patient patient);
}
