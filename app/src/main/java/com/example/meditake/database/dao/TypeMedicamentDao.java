package com.example.meditake.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;import com.example.meditake.database.entities.TypeMedicament;

import java.util.List;

@Dao
public interface TypeMedicamentDao {
    @Query("Select * from TypeMedicament")
    List<TypeMedicament> getAll();

    @Query("Select * from TypeMedicament where id = :id")
    TypeMedicament getById(int id);

    @Query("SELECT * FROM TypeMedicament WHERE id IN (:userIds)")
    List<TypeMedicament> loadAllByIds(int[] userIds);


    @Insert
    void insertAll(TypeMedicament ...typeMedicaments);

    @Delete
    void delete(TypeMedicament typeMedicament);
}
