package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class CriticalDrugActivity extends BaseActivity {

    Button confirmButton;
    TextView criticalDrugTextView, messageTextView, morphineTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical_drug);

        criticalDrugTextView = findViewById(R.id.criticalDrugTextView);
        messageTextView = findViewById(R.id.messageTextView);
        morphineTextView = findViewById(R.id.morphineTextView);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                }
                }




        );



    }
}
