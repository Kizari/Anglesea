package com.example.anglesea.Activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Dialogs.SignatureDialog;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

import java.text.DecimalFormat;

public class CalculationActivity extends BaseActivity
{
    double mValue, mWeight;

    public boolean mIsPediatric;
    public String mSignature;
    public double mFinal;

    TextView textName, textStrength;
    TextView textDosage1, textDosage2;
    TextView textTopFraction, textBottomFraction;
    TextView textTotal1, textTotal2, textTotal3;
    TextView textFinal;
    LinearLayout layoutRow1, layoutRow2;

    public Drug mDrug;
    public String mNHI;
    public String mRN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mIsPediatric = getIntent().getExtras().getBoolean("isPediatric");
        mRN = getIntent().getExtras().getString("rn");
        mNHI = getIntent().getExtras().getString("nhi");

        String titleString = mIsPediatric ? "Pediatric Calculation" : "Adult Calculation";

        SpannableString s = new SpannableString(titleString);
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        mDrug = mDatabase.drug().getById(getIntent().getExtras().getLong("drugId"));
        mValue = getIntent().getExtras().getDouble("value");
        if(mIsPediatric) mWeight = getIntent().getExtras().getDouble("weight");

        textName = findViewById(R.id.textName);
        textStrength = findViewById(R.id.textStrength);

        if(!mDrug.isDangerous())
        {
            ImageView imageDangerous = findViewById(R.id.imageDangerous);
            imageDangerous.setVisibility(View.GONE);
        }

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

        LinearLayout layoutRow1 = findViewById(R.id.layoutRow1);
        LinearLayout layoutRow2 = findViewById(R.id.layoutRow2);

        DecimalFormat format = new DecimalFormat("###,###.##");

        double dosage;

        if(mIsPediatric)
        {
            textDosage1.setText(mValue + "mg/kg x " + mWeight + "kg");
            dosage = mValue * mWeight;
            textDosage2.setText(format.format(dosage) + "mg");
        }
        else
        {
            layoutRow1.setVisibility(View.GONE);
            layoutRow2.setVisibility(View.GONE);
            dosage = mValue;
        }

        textTopFraction.setText(format.format(dosage) + "mg");
        textBottomFraction.setText(format.format(mDrug.getMg()) + "mg");
        textTotal1.setText(" x " + format.format(mDrug.getMl()) + "ml");

        double flat = dosage / mDrug.getMg();
        textTotal2.setText(format.format(flat) + "  x " + format.format(mDrug.getMl()) + "ml");

        double total = flat * mDrug.getMl();
        textTotal3.setText(format.format(total) + "ml");

        textFinal.setText(Math.round(total) + "ml");
        mFinal = Math.round(total);
    }

    public void onVerify(View v)
    {
        SignatureDialog.Create(this);
    }
}