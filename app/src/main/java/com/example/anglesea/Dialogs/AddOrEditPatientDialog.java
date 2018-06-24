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

public class AddOrEditPatientDialog extends BaseDialog implements View.OnClickListener
{
    private AddOrEditPatientDialog(Context context)
    {
        super(context);
    }

    public static void Create(Context context)
    {
        AddOrEditPatientDialog dialog = new AddOrEditPatientDialog(context);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addoredit_patient);

        Button buttonClose = findViewById(R.id.buttonClose);
        buttonClose.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonClose:
                dismiss();
                break;
        }
    }
}
