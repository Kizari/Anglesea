package com.example.anglesea.DataAccess.Drug;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.example.anglesea.Entities.DrugType;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DrugDao
{
    @Insert(onConflict = IGNORE)
    long insert(Drug drug);

    @Query("SELECT * FROM drug")
    List<Drug> getAll();

    @Query("SELECT * FROM drug WHERE type = :type OR type = 3")
    List<Drug> getAllByType(short type);

    @Query("SELECT * FROM drug WHERE isDangerous = 0 AND type = :type OR type = 3")
    List<Drug> getAllSafe(short type);

    @Query("SELECT * FROM drug WHERE isDangerous = 1 AND type = :type OR type = 3")
    List<Drug> getAllDangerous(short type);

    @Query("SELECT * FROM drug WHERE id = :id")
    Drug getById(long id);

    @Query("SELECT * FROM drug WHERE name = :name")
    Drug getByName(String name);

    @Update
    void update(Drug... drugs);

    @Delete
    void delete(Drug drug);
}
