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
}, version = 13)
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
            Nurse nurse = new Nurse("ABC1234", "Joy Miller", "password");
            instance.nurse().insert(nurse);
        }

        // Populate Drug Table
        if(instance.drug().getAll().size() < 1)
        {
            Drug drug5 = new Drug();
            drug5.setName("Paracetamol Syrup");
            drug5.setMg(250);
            drug5.setMl(250);
            drug5.setRedDrug(false);
            instance.drug().insert(drug5);

            Drug drug6 = new Drug();
            drug6.setName("Paracetamol Syrup");
            drug6.setMg(120);
            drug6.setMl(5);
            drug6.setRedDrug(false);
            instance.drug().insert(drug6);

            Drug drug3 = new Drug();
            drug3.setName("Ibupofen Syrup");
            drug3.setMg(100);
            drug3.setMl(5);
            drug3.setRedDrug(false);
            instance.drug().insert(drug3);

            Drug drug4 = new Drug();
            drug4.setName("Morphine Syrup");
            drug4.setRedDrug(true);
            drug4.setMg(100);
            drug4.setMl(5);
            instance.drug().insert(drug4);

            Drug drug1 = new Drug();
            drug1.setName("Cyclizine Syrup");
            drug1.setMg(50);
            drug1.setMl(1);
            drug1.setRedDrug(false);
            instance.drug().insert(drug1);

            Drug drug2 = new Drug();
            drug2.setName("Droperidol Syrup");
            drug2.setMg(2.5);
            drug2.setMl(1);
            drug2.setRedDrug(false);
            instance.drug().insert(drug2);
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