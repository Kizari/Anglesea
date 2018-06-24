package com.example.anglesea.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

public class RegistrationActivity extends BaseActivity {

    private EditText RNTxt;
    private EditText FNTxt;
    private EditText LNTxt;
    private EditText PWTxt;
    private Button CancelBtn;
    private Button RegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //CancelBtn = (Button) findViewById(R.id.CancelBtn);
        RegBtn = (Button) findViewById(R.id.RegBtn);
        RNTxt = (EditText) findViewById(R.id.RNTxt);
        FNTxt = (EditText) findViewById(R.id.FNTxt);
        PWTxt = (EditText) findViewById(R.id.PWTxt);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

            }
        });

        /*CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel();
            }
        });*/

    }

    private Boolean validate() {

        String RN = RNTxt.getText().toString().trim();
        String FullName = FNTxt.getText().toString();
        String Password = PWTxt.getText().toString();

        if(RN.isEmpty() || FullName.isEmpty()  || Password.isEmpty() )
        {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        Nurse exists = mDatabase.nurse().getByRN(RN);
        if(exists != null){
            Helper.toast(this, "RN already registered.Try logging in");
            return false;
        }

        //Create new nurse object
        Nurse nurse = new Nurse(RN, FullName, Password);

        // Insert the nurse into the database
        mDatabase.nurse().insert(nurse);

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void Cancel(){
        Intent canIntent = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(canIntent);
    }

}
