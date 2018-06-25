package com.example.anglesea.Dialogs;

import com.example.anglesea.Activities.CalculationActivity;
import com.example.anglesea.Activities.RoomActivity;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrecalculationDialog extends BaseDialog implements View.OnClickListener
{
    public static void Create(RoomActivity activity, long drugId)
    {
        PrecalculationDialog dialog = new PrecalculationDialog(activity, drugId);
        dialog.showFull(dialog);
    }

    private PrecalculationDialog(RoomActivity activity, long drugId)
    {
        super(activity);
        mActivity = activity;
        mDrugId = drugId;
    }

    RoomActivity mActivity;
    EditText mValue, mWeight;
    TextView textValue, textWeight;
    long mDrugId;
    boolean mIsPediatric;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_precalculation);

        Button buttonClose = findViewById(R.id.buttonClose);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        mValue = findViewById(R.id.editValue);
        mWeight = findViewById(R.id.editWeight);
        textValue = findViewById(R.id.textValue);
        textWeight = findViewById(R.id.textWeight);

        buttonClose.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);

        int age = Helper.calculateAge(mActivity.mPatient.getDOB());

        if(age <= 16)
        {
            mIsPediatric = true;
        }
        else
        {
            mIsPediatric = false;
            textValue.setText("Dosage");
            mValue.setHint("mg");
            textWeight.setVisibility(View.GONE);
            mWeight.setVisibility(View.GONE);
        }
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonClose:
                dismiss();
                break;
            case R.id.buttonSubmit:
                startCalculation();

                break;
        }
    }

    private void startCalculation()
    {
        if(mValue.getText().toString().equals("") || (mIsPediatric && mWeight.getText().toString().equals("")))
        {
            Helper.toast(mContext, "Please ensure all fields are filled out");
            return;
        }

        double weight = 0;
        double value;

        try
        {
            if(mIsPediatric)
                weight = Double.parseDouble(mWeight.getText().toString());

            value = Double.parseDouble(mValue.getText().toString());
        }
        catch(Exception ex)
        {
            Helper.toast(mContext, "Please ensure all values are numeric and do not include units");
            return;
        }

        Intent intent = new Intent(mContext, CalculationActivity.class);
        intent.putExtra("nhi", mActivity.mPatient.getNHI());
        intent.putExtra("drugId", mDrugId);
        intent.putExtra("value", value);
        intent.putExtra("isPediatric", mIsPediatric);
        intent.putExtra("rn", mActivity.mRN);
        if(mIsPediatric) intent.putExtra("weight", weight);
        mContext.startActivity(intent);

        dismiss();
    }
}
