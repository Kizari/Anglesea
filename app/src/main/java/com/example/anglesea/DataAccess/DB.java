package com.example.anglesea.DataAccess;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Drug.DrugDao;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Patient.PatientDao;
import com.example.anglesea.DataAccess.Room.RoomDao;

@Database(entities =
{
        Patient.class,
        Drug.class,
        com.example.anglesea.DataAccess.Room.Room.class
}, version = 3)
public abstract class DB extends RoomDatabase
{
    private static DB instance;

    public abstract PatientDao patient();
    public abstract DrugDao drug();
    public abstract RoomDao room();

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