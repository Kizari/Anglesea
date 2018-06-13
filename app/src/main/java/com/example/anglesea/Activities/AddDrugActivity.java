package com.example.anglesea.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;
import android.widget.CheckBox;


public class AddDrugActivity extends BaseActivity
{
    private static final String TAG = "AddDrugActivity ";
    private FloatingActionButton saveButton;
    private  EditText addDrugText;
    private EditText strengthText;
    private CheckBox redDrugCheckBox;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drug);
        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        addDrugText = (EditText) findViewById(R.id.addDrugEditText);
        strengthText = findViewById(R.id.strengthEditText);
        redDrugCheckBox = findViewById(R.id.redDrugChecfkBox);
    }

    public void newDrugOnClick(View v) {
        // Create a new drug object
        Drug drug = new Drug();

        // Write an if statement to validate empty text boxes

        // Try/catch on the Integer.getInteger to make sure it's a string

        // Set the name of the drug to the text box value
        drug.setName(addDrugText.getText().toString());

        // Set the strength of the drug to the text box value
        int strength = 0;
        try
        {
            strength = Integer.parseInt(strengthText.getText().toString());
        }
        catch(Exception ex)
        {
            Toast.makeText(this, "Please enter a valid number for the strength value.", Toast.LENGTH_LONG).show();
            return;
        }

        drug.setStrength(strength);
        CheckBox checkBox = findViewById(R.id.redDrugChecfkBox);

       if(checkBox.isChecked())
            drug.setRedDrug(true);
       else
           drug.setRedDrug(false);

        // Insert the drug object into the database
        mDatabase.drug().insert(drug);

        // Close this activity, go back to the list
        finish();
    }
}