package com.example.anglesea.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.anglesea.R;

public class IVOralActivity extends AppCompatActivity {

    private ImageButton IVBtn;
    private ImageButton OralBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivoral);

        IVBtn = (ImageButton)findViewById(R.id.IVBtn);
        OralBtn = (ImageButton)findViewById(R.id.OralBtn);

        IVBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IVOralActivity.this, AddDrugActivity.class);
                startActivity(intent);
            }
        });

        OralBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IVOralActivity.this, AddDrugActivity.class);
                startActivity(intent);
            }
        });

    }
}
