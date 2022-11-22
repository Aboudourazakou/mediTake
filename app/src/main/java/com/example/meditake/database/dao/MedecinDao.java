package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.meditake.database.entities.Medecin;

import java.util.List;

@Dao
public interface MedecinDao {
    @Query("Select * from Medecin")
    List<Medecin> getAll();

    @Query("Select * from Medecin where id = :id")
    Medecin getById(int id);

    @Query("SELECT * FROM Medecin WHERE id IN (:userIds)")
    List<Medecin> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Medecin WHERE nom LIKE :first or " +
            "prenom LIKE :last LIMIT 1")
    Medecin findByName(String first, String last);


    @Insert
    void insertAll(Medecin ...medecins);

    @Delete
    void delete(Medecin medecin);
}
