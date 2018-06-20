package com.example.anglesea.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        SpannableString s = new SpannableString(room.getRoomName() + " Details");
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        TextView textFullName = findViewById(R.id.textFullName);
        TextView textNHI = findViewById(R.id.textNHI);
        TextView textAge = findViewById(R.id.textAge);

        textFullName.setText(patient.getFullName());
        textNHI.setText(patient.getNHI());
        textAge.setText(String.valueOf(mHelper.calculateAge(patient.getDOB())));
    }

    public void buttonAdminister_clicked(View v)
    {
        Intent intent = new Intent(this, IVOralActivity.class);
        intent.putExtra("nhi", patient.getNHI());
        startActivity(intent);
    }
}
