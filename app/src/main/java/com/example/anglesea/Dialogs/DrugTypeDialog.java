package com.example.anglesea.Dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.anglesea.Activities.IVOralActivity;
import com.example.anglesea.Activities.PatientDetailActivity;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.DrugType;
import com.example.anglesea.R;

public class DrugTypeDialog extends BaseDialog implements View.OnClickListener
{
    private String mNHI;

    private DrugTypeDialog(Context context, String nhi)
    {
        super(context);
        mNHI = nhi;
    }

    public static void Create(Context context, String nhi)
    {
        DrugTypeDialog dialog = new DrugTypeDialog(context, nhi);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_drug_type);

        ImageButton buttonIV = findViewById(R.id.IVBtn);
        ImageButton buttonOral = findViewById(R.id.OralBtn);
        Button buttonClose = findViewById(R.id.buttonClose);

        buttonIV.setOnClickListener(this);
        buttonOral.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.IVBtn:
                Intent intent = new Intent(mContext, PatientDetailActivity.class);
                intent.putExtra("drugType", DrugType.INTRAVENOUS);
                mContext.startActivity(intent);
                dismiss();
                break;
            case R.id.OralBtn:
                Intent intent2 = new Intent(mContext, PatientDetailActivity.class);
                intent2.putExtra("drugType", DrugType.ORAL);
                mContext.startActivity(intent2);
                dismiss();
                break;
            case R.id.buttonClose:
                dismiss();
                break;
        }
    }
}
