package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.meditake.database.entities.Programme;
import com.example.meditake.database.entities.ProgrammeWithRappel;
import com.example.meditake.database.entities.ProgrammeWithRappelWithRapportAndMedicament;

import java.util.List;

@Dao
public interface ProgrammeDao {
    @Query("Select * from Programme")
    List<Programme> getAll();

    @Insert
    long insert(Programme p);

    @Query("Select * from Programme where id = :id")
    Programme getById(long id);

    @Query("SELECT * FROM Programme WHERE id IN (:userIds)")
    List<Programme> loadAllByIds(int[] userIds);

    @Insert
    void insertAll(Programme ...programmes);

    @Delete
    void delete(Programme programme);

    @Transaction
    @Query("SELECT * FROM PROGRAMME")
    List<ProgrammeWithRappel> getProgrammesWithRappels();

    @Transaction
    @Query("select * from programme where id = :id")
    ProgrammeWithRappel getProgrammeWithRappels(Long id);

    @Transaction
    @Query("SELECT * FROM PROGRAMME")
    List<ProgrammeWithRappelWithRapportAndMedicament> getProgrammes();

    @Transaction
    @Query("select * from programme where id = :id")
    ProgrammeWithRappelWithRapportAndMedicament getProgramme(Long id);

}
