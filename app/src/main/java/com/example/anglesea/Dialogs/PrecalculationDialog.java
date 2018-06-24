package com.example.anglesea.Dialogs;

import com.example.anglesea.Activities.CalculationActivity;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PrecalculationDialog extends BaseDialog implements View.OnClickListener
{
    public static void Create(Context context, long drugId)
    {
        PrecalculationDialog dialog = new PrecalculationDialog(context, drugId);
        dialog.showFull(dialog);
    }

    private PrecalculationDialog(Context context, long drugId)
    {
        super(context);
        mDrugId = drugId;
    }

    EditText mValue, mWeight;
    long mDrugId;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_precalculation);

        Button buttonClose = findViewById(R.id.buttonClose);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        mValue = findViewById(R.id.editValue);
        mWeight = findViewById(R.id.editWeight);

        buttonClose.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
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
        if(mValue.getText().toString().equals("") || mWeight.getText().toString().equals(""))
        {
            Helper.toast(mContext, "Please ensure all fields are filled out");
            return;
        }

        double weight;
        double value;

        try
        {
            weight = Double.parseDouble(mWeight.getText().toString());
            value = Double.parseDouble(mValue.getText().toString());
        }
        catch(Exception ex)
        {
            Helper.toast(mContext, "Please ensure all values are numeric and do not include units");
            return;
        }

        Intent intent = new Intent(mContext, CalculationActivity.class);
        intent.putExtra("drugId", mDrugId);
        intent.putExtra("value", value);
        intent.putExtra("weight", weight);
        mContext.startActivity(intent);

        dismiss();
    }
}
