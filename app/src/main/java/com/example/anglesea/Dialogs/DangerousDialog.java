package com.example.anglesea.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anglesea.Activities.CalculationActivity;
import com.example.anglesea.Activities.HomeActivity;
import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

public class DangerousDialog extends BaseDialog implements View.OnClickListener
{
    CalculationActivity mActivity;

    private DangerousDialog(CalculationActivity activity)
    {
        super(activity);
        mActivity = activity;
    }

    public static void Create(CalculationActivity activity)
    {
        DangerousDialog dialog = new DangerousDialog(activity);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dangerous);

        Button buttonVerify = findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(this);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
    }

    private void verify()
    {
        Administration administration = new Administration();
        administration.setQuantity(mActivity.mFinal);
        administration.setDrugId(mActivity.mDrug.getId());
        administration.setNHI(mActivity.mNHI);
        administration.setRN(mActivity.mRN);
        administration.setSignature(mActivity.mSignature);
        administration.setTimestamp(System.currentTimeMillis());
        mDatabase.administration().insert(administration);

        Helper.toast(mActivity, "Administration Complete");

        Intent intent = new Intent(mActivity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
        intent.putExtra("rn", mActivity.mRN);
        mActivity.startActivity(intent);
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonCancel:
                dismiss();
                break;
            case R.id.buttonVerify:
                verify();
                dismiss();
                break;
        }
    }
}