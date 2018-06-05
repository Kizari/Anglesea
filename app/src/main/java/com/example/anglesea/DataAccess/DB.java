package com.example.anglesea.DataAccess;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Patient.PatientDao;

@Database(entities = { Patient.class }, version = 1)
public abstract class DB extends RoomDatabase
{
    private static DB instance;

    public abstract PatientDao patient();

    public static DB get(Context context)
    {
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), DB.class, "PersonalOrganiserDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        return instance;
    }

    public static void destroyInstance()
    {
        instance = null;
    }
}