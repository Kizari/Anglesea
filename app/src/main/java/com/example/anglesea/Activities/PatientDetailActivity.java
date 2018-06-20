package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class PatientDetailActivity extends BaseActivity
{
    Room room;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        room = mDatabase.room().getByName(getIntent().getStringExtra("room"));
        patient = mDatabase.patient().getByRoom(room.getId());
    }
}
