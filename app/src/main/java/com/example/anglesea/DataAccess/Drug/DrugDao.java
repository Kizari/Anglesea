package com.example.anglesea.DataAccess.Drug;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.anglesea.DataAccess.Patient.Patient;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DrugDao
{
    @Insert(onConflict = IGNORE)
    long insert(Drug drug);

    @Query("SELECT * FROM drug")
    List<Drug> getAll();

    @Query("SELECT * FROM drug WHERE isRedDrug = 0 AND isIntravenous = :isIntravenous")
    List<Drug> getAllSafe(boolean isIntravenous);

    @Query("SELECT * FROM drug WHERE isRedDrug = 1 AND isIntravenous = :isIntravenous")
    List<Drug> getAllDangerous(boolean isIntravenous);

    @Query("SELECT * FROM drug WHERE id = :id")
    Drug getById(long id);

    @Query("SELECT * FROM drug WHERE name = :name")
    Drug getByName(String name);

    @Update
    void update(Drug... drugs);

    @Delete
    void delete(Drug drug);
}
