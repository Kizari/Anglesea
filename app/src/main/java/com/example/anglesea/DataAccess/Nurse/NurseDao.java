package com.example.anglesea.DataAccess.Nurse;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


/**
 * Created by sarab on 6/12/2018.
 */
@Dao
public interface NurseDao
{
    @Insert(onConflict = IGNORE)
    void insert(Nurse nurse); // Nurse will be inserted with ID predefined, so we don't need to return the ID, therefore we make this void

    @Query("SELECT * FROM nurse")
    List<Nurse> getAll();

    @Query("SELECT * FROM nurse WHERE RN = :RN")
    Nurse getByRN(String RN);

    @Delete
    void delete(Nurse nurse);

}
