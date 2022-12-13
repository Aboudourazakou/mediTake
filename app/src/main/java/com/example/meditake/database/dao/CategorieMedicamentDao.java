package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;import com.example.meditake.database.entities.CategorieMedicament;
import com.example.meditake.database.entities.Medicament;

import java.util.List;

@Dao
public interface CategorieMedicamentDao {
    @Query("Select * from CategorieMedicament")
    List<CategorieMedicament> getAll();

    @Query("Select * from CategorieMedicament where id = :id")
    CategorieMedicament getById(int id);

    @Query("SELECT * FROM CategorieMedicament WHERE id IN (:userIds)")
    List<CategorieMedicament> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(CategorieMedicament... categorieMedicaments);

    @Insert
    long insert(CategorieMedicament catmedicament);

    @Delete
    void delete(CategorieMedicament categorieMedicament);
    @Query("DELETE FROM CATEGORIEMEDICAMENT")
    void deleteAll();
}
