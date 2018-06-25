package com.example.anglesea.DataAccess.Chart;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.anglesea.DataAccess.Drug.Drug;

import java.util.List;

@Dao
public interface ChartDao
{
    @Query("SELECT * FROM chart")
    List<Chart> getAll();

    @Query("SELECT * FROM chart WHERE NHI = :NHI")
    List<Chart> getByNHI(String NHI);

    @Query("SELECT * FROM chart WHERE id = :id")
    Chart getById(long id);

    @Insert
    long insert(Chart chart);
}
