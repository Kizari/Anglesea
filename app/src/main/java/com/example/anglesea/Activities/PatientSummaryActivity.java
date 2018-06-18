package com.example.anglesea.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;


import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static java.lang.String.valueOf;

public class PatientSummaryActivity extends BaseActivity
{
    Button  closeButton;
    TextView fullNameTextView, nhiNoTextView, roomTextView, adminDrugTextView;
    EditText  nhiEditText, roomEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_summary);


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

        // Get patient
        Patient patient = mDatabase.patient().getByNHI("ABC1234");
        fullNameTextView.setText(patient.getFullName());

        // Get patient room
        Room room = mDatabase.room().getById(patient.getRoomId());
        roomTextView.setText(room.getRoomName());

        // Get all the administrations for this patient
        List<Administration> administrations = mDatabase.administration().getByNHI("ABC1234");

        // This will store the final results to be displayed
        List<Result> results = new ArrayList<Result>();

        // Loop through each of the administrations
        for(Administration administration : administrations)
        {
            // Get the drug that matches the administration
            Drug drug = mDatabase.drug().getById(administration.getDrugId());

            // Check if the drug is already stored in the results
            Result exists = null;
            for(Result r : results)
            {
                if(r.mDrug.getId() == drug.getId())
                    exists = r;
            }

            // If the drug was already there, we add to the quantity
            if(exists != null)
            {
                exists.mQuantity += administration.getQuantity();
            }
            else
            {
                // Add a new result to the table
                Result result = new Result();
                result.mDrug = drug;
                result.mQuantity = administration.getQuantity();
                results.add(result);
            }
        }

        ListView resultList = findViewById(R.id.resultList);
        resultList.setAdapter(new ResultAdapter(this, results));
    }

    private class ResultAdapter extends ArrayAdapter<Result>
    {
        public ResultAdapter(Context context, List<Result> results)
        {
            super(context, 0, results);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            if(convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_result, parent, false);

            TextView drugName = convertView.findViewById(R.id.drugName);
            TextView drugQuantity = convertView.findViewById(R.id.drugQuantity);

            Result result = getItem(position);

            drugName.setText(result.mDrug.getName());
            drugQuantity.setText(valueOf(result.mQuantity));

            return convertView;
        }
    }

    private class Result
    {
        public Drug mDrug;
        public double mQuantity;
    }
}
