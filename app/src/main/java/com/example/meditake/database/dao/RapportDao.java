package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.RappelWithRapportAndMedicament;
import com.example.meditake.database.entities.Rapport;

import java.util.List;

@Dao
public interface RapportDao {
    @Query("Select * from Rapport")
    List<Rapport> getAll();

    @Query("select * from rapport where idRappel = :id")
    List<Rapport> findRapportByIdRappel(long id);

    @Insert
    long insert(Rapport p);

    @Query("Select * from Rapport where id = :id")
    Rapport getById(long id);

    @Query("SELECT * FROM Rapport WHERE id IN (:userIds)")
    List<Rapport> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(Rapport ...rapports);

    @Delete
    void delete(Rapport rapport);
    @Query("select * from rapport where idRappel = :id")
    List<Rapport> findRapportByIdRappel(long id);
}
