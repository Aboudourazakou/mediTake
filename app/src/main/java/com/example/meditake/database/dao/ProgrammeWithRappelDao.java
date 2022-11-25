package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.meditake.database.entities.ProgrammeWithRappel;
import com.example.meditake.database.entities.RappelWithRapportAndMedicament;

import java.util.List;

@Dao
public interface ProgrammeWithRappelDao {
    @Transaction
    @Query("SELECT * FROM PROGRAMME")
    List<ProgrammeWithRappel> getProgrammesWithRappels();

    @Transaction
    @Query("select * from programme where id = :id")
    ProgrammeWithRappel getProgrammeWithRappels(Long id);


}
