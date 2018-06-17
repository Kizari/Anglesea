package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
;

import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class PatientSummaryActivity extends BaseActivity
{
   Button  closeButton;
    TextView  patientSummaryTextView, fullNameTextView, nhiNoTextView, roomTextView, adminDrugTextView;
     EditText  nhiEditText, roomEditText;

    String[] medication =
            {
                    "Paracetomol",
                    "Ibuprofen",
                    "Morphine"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);


        patientSummaryTextView =  findViewById(R.id.patientSummaryTextView);
        fullNameTextView = findViewById(R.id.fullNameTextView);
        nhiNoTextView =  findViewById(R.id.nhiNoTextView);
        roomTextView =  findViewById(R.id.roomTextView);
        adminDrugTextView =  findViewById(R.id.adminDrugTextView);
        nhiEditText = findViewById(R.id.nhiEditText);
        roomEditText =  findViewById(R.id.roomEditText);

        closeButton = (Button) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });





    }
}
