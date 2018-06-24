package com.example.anglesea.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.R;

import java.text.DecimalFormat;

public class CalculationActivity extends BaseActivity
{
    double mValue, mWeight;

    TextView textName, textStrength;
    TextView textDosage1, textDosage2;
    TextView textTopFraction, textBottomFraction;
    TextView textTotal1, textTotal2, textTotal3;
    TextView textFinal;

    Drug mDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mDrug = mDatabase.drug().getById(getIntent().getExtras().getLong("drugId"));
        mValue = getIntent().getExtras().getDouble("value");
        mWeight = getIntent().getExtras().getDouble("weight");

        textName = findViewById(R.id.textName);
        textStrength = findViewById(R.id.textStrength);

        textName.setText(mDrug.getName());
        textStrength.setText(mDrug.getMg() + "mg / " + mDrug.getMl() + "ml");

        textDosage1 = findViewById(R.id.textDosage1);
        textDosage2 = findViewById(R.id.textDosage2);

        textTopFraction = findViewById(R.id.textTopFraction);
        textBottomFraction = findViewById(R.id.textBottomFraction);

        textTotal1 = findViewById(R.id.textTotal1);
        textTotal2 = findViewById(R.id.textTotal2);
        textTotal3 = findViewById(R.id.textTotal3);

        textFinal = findViewById(R.id.textFinal);

        DecimalFormat format = new DecimalFormat("###,###.00");

        textDosage1.setText(mValue + "mg/kg x " + mWeight + "kg");
        double dosage = mValue * mWeight;
        textDosage2.setText(format.format(dosage) + "mg");

        textTopFraction.setText(format.format(dosage) + "mg");
        textBottomFraction.setText(format.format(mDrug.getMg()) + "mg");
        textTotal1.setText(" x " + format.format(mDrug.getMl()) + "ml");

        double flat = dosage / mDrug.getMg();
        textTotal2.setText(format.format(flat) + "  x " + format.format(mDrug.getMl()) + "ml");

        double total = flat * mDrug.getMl();
        textTotal3.setText(format.format(total) + "ml");

        textFinal.setText(Math.round(total) + "ml");
    }
}