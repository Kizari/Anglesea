package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class PatientSummaryActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);
    }
}