package com.example.anglesea.Dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anglesea.Activities.CalculationActivity;
import com.example.anglesea.Activities.HomeActivity;
import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.DrugType;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureDialog extends BaseDialog implements View.OnClickListener
{
    CalculationActivity mActivity;

    private SignatureDialog(CalculationActivity activity)
    {
        super(activity);
        mActivity = activity;
    }

    public static void Create(CalculationActivity activity)
    {
        SignatureDialog dialog = new SignatureDialog(activity);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sign_first);

        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(this);

        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(this);
    }

    private void onConfirm()
    {
        SignaturePad signature = findViewById(R.id.signature);
        mActivity.mSignature = signature.getSignatureSvg();

        if(mActivity.mIsPediatric || mActivity.mDrug.getType() == DrugType.INTRAVENOUS || mActivity.mDrug.isDangerous())
        {
            DangerousDialog.Create(mActivity);
        }
        else
        {
            Administration administration = new Administration();
            administration.setQuantity(mActivity.mFinal);
            administration.setDrugId(mActivity.mDrug.getId());
            Patient patient = mDatabase.patient().getByNHI(mActivity.mNHI);
            administration.setChartId(patient.getChartId());
            administration.setRN(mActivity.mRN);
            administration.setSignature(mActivity.mSignature);
            administration.setTimestamp(System.currentTimeMillis());
            administration.setRoomId(patient.getRoomId());
            mDatabase.administration().insert(administration);

            Helper.toast(mActivity, "Administration Complete");

            Intent intent = new Intent(mActivity, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
            intent.putExtra("rn", mActivity.mRN);
            mActivity.startActivity(intent);
        }

        dismiss();
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonClose:
                dismiss();
                break;
            case R.id.buttonConfirm:
                onConfirm();
                break;
        }
    }
}
