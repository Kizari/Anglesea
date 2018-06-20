package com.example.anglesea.DataAccess.Patient;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PatientDao
{
    @Insert(onConflict = IGNORE)
    long insert(Patient patient);

    @Query("SELECT * FROM patient")
    List<Patient> getAll();

    @Query("SELECT * FROM patient WHERE roomId = :roomId")
    Patient getByRoom(int roomId);

    @Query("SELECT * FROM patient WHERE NHI = :NHI")
    Patient getByNHI(String NHI);

    @Delete
    void delete(Patient patient);
}