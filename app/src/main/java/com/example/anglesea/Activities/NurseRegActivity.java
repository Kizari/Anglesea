package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anglesea.DataAccess.Nurse.Nurse;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class NurseRegActivity extends BaseActivity {

    private EditText RNTxt;
    private EditText FNTxt;
    private EditText LNTxt;
    private EditText PWTxt;
    private Button CancelBtn;
    private Button RegBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_reg);

        CancelBtn = (Button) findViewById(R.id.CancelBtn);
        RegBtn = (Button) findViewById(R.id.RegBtn);
        RNTxt = (EditText) findViewById(R.id.RNTxt);
        FNTxt = (EditText) findViewById(R.id.FNTxt);
        LNTxt = (EditText) findViewById(R.id.LNTxt);
        PWTxt = (EditText) findViewById(R.id.PWTxt);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RN = RNTxt.getText().toString().trim();
                String FirstName = FNTxt.getText().toString().trim();
                String LastName = LNTxt.getText().toString().trim();
                String Password = PWTxt.getText().toString().trim();

                //Create new nurse object
                Nurse nurse = new Nurse(RN, FirstName, LastName, Password);

                // Insert the nurse into the database
                mDatabase.nurse().insert(nurse);

            }
        });

    }

}
