package com.example.anglesea.Dialogs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;

public class SendPDFDialog extends BaseDialog implements View.OnClickListener
{
    AuditActivity mActivity;

    private SendPDFDialog(AuditActivity activity)
    {
        super(activity);
        mActivity = activity;
    }

    public static void Create(AuditActivity activity)
    {
        SendPDFDialog dialog = new SendPDFDialog(activity);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_send_pdf);

        Button buttonOpen = findViewById(R.id.buttonOpen);
        buttonOpen.setOnClickListener(this);
        Button buttonEmail = findViewById(R.id.buttonEmail);
        buttonEmail.setOnClickListener(this);
        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonOpen:
                onOpen();
                dismiss();
                break;
            case R.id.buttonEmail:
                onEmail();
                dismiss();
                break;
            case R.id.buttonClose:
                dismiss();
                break;
        }
    }

    private void onOpen()
    {
        Uri uri;

        try
        {
            uri = mActivity.saveToPdf();
        }
        catch (Exception ex)
        {
            Helper.toast(mActivity, "Failed to convert audit to PDF. Please try again later.");
            return;
        }

        try
        {
            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            myIntent.setData(uri);
            mActivity.startActivity(myIntent);
        }
        catch (Exception ex)
        {
            Helper.toast(mActivity, "Could not open PDF. Please ensure a PDF viewer app is installed.");
        }
    }

    private void onEmail()
    {
        Uri uri;

        try
        {
            uri = mActivity.saveToPdf();
        }
        catch (Exception ex)
        {
            Helper.toast(mActivity, "Failed to convert audit to PDF. Please try again later.");
            return;
        }

        try
        {
            String dest = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Anglesea-Audit" + (mActivity.mChartId + 10000) + ".pdf";
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Anglesea Dosage Calculator - Audit #" + (mActivity.mChartId + 10000));
            intent.putExtra(Intent.EXTRA_TEXT, "Hi\r\n\r\nThis is an automatically generated email from the Anglesea Dosage Calculator Android App.\r\nPlease find your audit PDF file attached.\r\n\r\nRegards\r\nAnglesea Dosage Calculator");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(dest)));
            mActivity.startActivity(Intent.createChooser(intent, "Send Email"));

        }
        catch (Exception ex)
        {
            Helper.toast(mActivity, "Could not open email client. Please ensure email is setup on your device.");
        }
    }
}