package com.example.anglesea.DataAccess;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Administration.AdministrationDao;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Drug.DrugDao;
import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.DataAccess.Nurse.NurseDao;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Patient.PatientDao;
import com.example.anglesea.DataAccess.Room.RoomDao;

import java.util.GregorianCalendar;

@Database(entities =
{
        Patient.class,
        Drug.class,
        com.example.anglesea.DataAccess.Room.Room.class,
        Nurse.class,
        Administration.class
}, version = 10)
public abstract class DB extends RoomDatabase
{
    private static DB instance;

    public abstract PatientDao patient();
    public abstract DrugDao drug();
    public abstract RoomDao room();
    public abstract NurseDao nurse();
    public abstract AdministrationDao administration();

    public static DB get(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), DB.class, "PersonalOrganiserDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            createTestData(instance);
        }

        return instance;
    }

    private static void createTestData(DB instance)
    {
        // Populate Nurse Table
        if(instance.nurse().getAll().size() < 1)
        {
            Nurse nurse = new Nurse("ABC1234", "Joy", "Miller", "password");
            instance.nurse().insert(nurse);
        }

        // Populate Drug Table
        if(instance.drug().getAll().size() < 1)
        {
            Drug drug = new Drug();
            drug.setName("Paracetamol");
            drug.setStrength(20.0f);
            drug.setRedDrug(false);
            instance.drug().insert(drug);
            drug = new Drug();
            drug.setName("Morphine");
            drug.setStrength(15.0f);
            drug.setRedDrug(true);
            instance.drug().insert(drug);
        }

        // Populate Room Table
        if(instance.room().getAll().size() < 1)
        {
            com.example.anglesea.DataAccess.Room.Room room;

            for(int i = 1; i <= 12; i++)
            {
                room = new com.example.anglesea.DataAccess.Room.Room();
                room.setRoomName("D" + i);
                instance.room().insert(room);
            }

            for(int i = 1; i <= 17; i++)
            {
                room = new com.example.anglesea.DataAccess.Room.Room();
                room.setRoomName("Rm" + i);
                instance.room().insert(room);
            }
        }

        // Populate Patient Table
        if(instance.patient().getAll().size() < 1)
        {
            Patient patient = new Patient();
            patient.setFullName("John Smith");
            patient.setNHI("IWE1238");
            patient.setDOB(new GregorianCalendar(1965, 8, 28).getTimeInMillis());
            patient.setRoomId(1);
            instance.patient().insert(patient);

            patient = new Patient();
            patient.setFullName("Jane Smith");
            patient.setNHI("POA9081");
            patient.setDOB(new GregorianCalendar(1976, 5, 12).getTimeInMillis());
            patient.setRoomId(17);
            instance.patient().insert(patient);
        }

        // Populate Administration Table
        if(instance.patient().getAll().size() < 1)
        {
            Administration administration = new Administration();
            administration.setNHI("IWE1238");
            administration.setDrugId(2);
            administration.setQuantity(24);
            instance.administration().insert(administration);

            administration = new Administration();
            administration.setNHI("IWE1238");
            administration.setDrugId(2);
            administration.setQuantity(12);
            instance.administration().insert(administration);

            administration = new Administration();
            administration.setNHI("IWE1238");
            administration.setDrugId(1);
            administration.setQuantity(50);
            instance.administration().insert(administration);
        }
    }

    public static void destroyInstance()
    {
        instance = null;
    }
}