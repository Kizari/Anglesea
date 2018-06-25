package com.example.anglesea.DataAccess.Administration;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.anglesea.DataAccess.Patient.Patient;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AdministrationDao
{
    @Insert(onConflict = IGNORE)
    long insert(Administration drug);

    @Query("SELECT * FROM administration")
    List<Administration> getAll();

    @Query("SELECT * FROM administration WHERE chartId = :chartId")
    List<Administration> getByChart(long chartId);

    @Query("SELECT MAX(timestamp) as max_date FROM administration WHERE chartId = :chartId AND drugId = :drugId")
    long getMaxDateForDrug(long chartId, long drugId);

    @Delete
    void delete(Administration drug);
}
