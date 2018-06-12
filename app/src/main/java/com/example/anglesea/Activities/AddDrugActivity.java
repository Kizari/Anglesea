package com.example.anglesea.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;


public class AddDrugActivity extends BaseActivity
{
    private static final String TAG = "AddDrugActivity ";
    FloatingActionButton saveButton;
    TextView addDrugText;
    DB myDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);

        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        addDrugText = (TextView) findViewById(R.id.addDrugText);

        //Intent i = new Intent(this,AddDrugActivity.class);
        //startService(i);

    }

    saveButton.setOnClickListener(new View.OnClickListener){
        @Override
                public void(View view){
            // I am still not able to save to the data base
        // So I am trying to see if this way will sort it out for me
        // so far not joy

    }
}
    public void newDrugOnClick(View v)
    {
        // Create a new drug object
        Drug drug = new Drug();
        drug.setName("Test Drug");

        // Insert the drug object into the database
        mDatabase.drug().insert(drug);
    }


    public void name (String newDrug){


        Drug drug = new Drug();
        drug.setName("Paracetamol");
        drug.setStrength(20);

        mDatabase.drug().insert(drug);

    }
}