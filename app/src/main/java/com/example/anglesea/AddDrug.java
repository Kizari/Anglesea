package com.example.anglesea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class AddDrug extends AppCompatActivity{
    private static final String TAG = "AddDrug ";
    Button saveButton;
    TextView addDrugText;
    Helper myDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_drug);

        saveButton = (Button) findViewById(R.id.saveButton);
        addDrugText = (TextView) findViewById(R.id.addDrugText);
        myDb = new Helper(this);

        Intent i = new Intent(this,AddDrug.class);
        startService(i);

    }

    public void newDrugOnClick(String newDrug){
        boolean insert = myDb.insertDrug(newDrug);
        if(insert == true){
            Toast.makeText(AddDrug.this,"Drug Added ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddDrug.this,"Drug not Added ", Toast.LENGTH_LONG).show();
        }

    }



}