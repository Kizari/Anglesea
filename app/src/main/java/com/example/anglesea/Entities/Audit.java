package com.example.anglesea.Entities;

import android.content.Context;

import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Chart.Chart;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;

public class Audit
{
    long id;
    Patient patient;
    Nurse nurse;
    Drug drug;
    Chart chart;
    double millilitres;
    String signature;
    long timestamp;
    Room room;

    public Audit(Administration administration, Context context)
    {
        DB database = DB.get(context);
        id = administration.getId();
        chart = database.chart().getById(administration.getChartId());
        patient = database.patient().getByNHI(chart.getNHI());
        nurse = database.nurse().getByRN(administration.getRN());
        drug = database.drug().getById(administration.getDrugId());
        millilitres = administration.getQuantity();
        signature = administration.getSignature();
        timestamp = administration.getTimestamp();
        room = database.room().getById(administration.getRoomId());
    }
}