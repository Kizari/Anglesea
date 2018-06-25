package com.example.anglesea.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anglesea.Activities.AuditActivity;
import com.example.anglesea.Activities.CalculationActivity;
import com.example.anglesea.Activities.HomeActivity;
import com.example.anglesea.Activities.RoomActivity;
import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

public class SummaryDialog extends BaseDialog implements View.OnClickListener
{
    RoomActivity mActivity;
    long mChartId;

    private SummaryDialog(RoomActivity activity, long chartId)
    {
        super(activity);
        mActivity = activity;
        mChartId = chartId;
    }

    public static void Create(RoomActivity activity, long chartId)
    {
        SummaryDialog dialog = new SummaryDialog(activity, chartId);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_summary);

        Button buttonVerify = findViewById(R.id.buttonView);
        buttonVerify.setOnClickListener(this);
        Button buttonCancel = findViewById(R.id.buttonLater);
        buttonCancel.setOnClickListener(this);

        TextView textMessage = findViewById(R.id.textMessage);
        String message = mActivity.mPatient.getFullName() + " has been discharged.\n\nA summary of this visit has been saved to your device and is available from the audit section found in the app menu.\n\n(Audit #1000" + mChartId + ")";
        textMessage.setText(message);
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonView:
                Intent intent = new Intent(mActivity, AuditActivity.class);
                intent.putExtra("chartId", mChartId);
                mActivity.startActivity(intent);
                dismiss();
                break;
            case R.id.buttonLater:
                dismiss();
                break;
        }
    }
}