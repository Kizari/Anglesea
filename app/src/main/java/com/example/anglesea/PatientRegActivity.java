package com.example.anglesea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.Entities.BaseActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class PatientRegActivity extends BaseActivity {

    private TextView NHINumber;
    private TextView Name;
    private DatePicker DateofBirth;
    private Button Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_reg);

        NHINumber = (TextView)findViewById(R.id.txt_NHINumber);
        Name = (TextView)findViewById(R.id.txt_FullName);
        DateofBirth = findViewById(R.id.dpk_DOB);
        Save = (Button)findViewById(R.id.btn_Save);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NHI = NHINumber.getText().toString().trim();
                String name = Name.getText().toString().trim();

                // Extract the date parts from the date picker control
                int day = DateofBirth.getDayOfMonth();
                int month = DateofBirth.getMonth();
                int year = DateofBirth.getYear();

                // Create a calendar object from the date parts
                Calendar calendar = new GregorianCalendar(year, month, day);

                //Create new nurse object
                Patient patient = new Patient();
                patient.setNHI(NHI);
                patient.setFullName(name);
                patient.setDOB(calendar.getTimeInMillis()); // Convert the date to a long for storage in the DB

                // Insert the patient into the database
                mDatabase.patient().insert(patient);

            }
        });

    }
}
