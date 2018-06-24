package com.example.anglesea.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.anglesea.Activities.RoomActivity;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.DrugType;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

public class AddOrEditDrugDialog extends Dialog implements View.OnClickListener
{
    public static AddOrEditDrugDialog Create(RoomActivity activity, Drug drug, boolean isDangerous)
    {
        AddOrEditDrugDialog dialog = new AddOrEditDrugDialog((RoomActivity)activity, drug, isDangerous);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    private RoomActivity mActivity;
    private Drug mDrug;
    private boolean mIsDangerous;

    EditText editMg, editMl, editName;
    Button buttonSave, buttonClose, buttonDelete;
    RadioButton radioOral, radioIV, radioBoth;
    CheckBox checkDangerous;

    private AddOrEditDrugDialog(RoomActivity activity, Drug drug, boolean isDangerous)
    {
        super(activity);
        mActivity = activity;
        mDrug = drug;
        mIsDangerous = isDangerous;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_addoredit_drug);

        buttonSave = findViewById(R.id.buttonSave);
        buttonClose = findViewById(R.id.buttonClose);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        radioOral = findViewById(R.id.radioOral);
        radioIV = findViewById(R.id.radioIV);
        radioBoth = findViewById(R.id.radioBoth);
        checkDangerous = findViewById(R.id.checkDangerous);

        TextView textTitle = findViewById(R.id.textTitle);
        editMg = findViewById(R.id.editMg);
        editMl = findViewById(R.id.editMl);
        editName = findViewById(R.id.editName);

        if(mDrug == null)
        {
            textTitle.setText("New Drug");
            buttonDelete.setText("Cancel");
            mDrug = new Drug();
        }
        else
        {
            textTitle.setText("Editing Drug");
            editName.setText(mDrug.getName());
            editMg.setText(Helper.format(mDrug.getMg()));
            editMl.setText(Helper.format(mDrug.getMl()));

            if(mDrug.getType() == DrugType.INTRAVENOUS)
                radioIV.toggle();
            else if(mDrug.getType() == DrugType.ORAL)
                radioOral.toggle();
            else
                radioBoth.toggle();

            if(mDrug.isDangerous())
                checkDangerous.setChecked(true);
        }
    }

    private void saveDrug()
    {
        if(editMg.getText().toString().equals("") || editMl.getText().toString().equals("") || editName.getText().toString().equals(""))
        {
            Helper.toast(mActivity, "Please ensure all fields are filled");
            return;
        }
        else if(!radioOral.isChecked() && !radioIV.isChecked())
        {
            Helper.toast(mActivity, "Please select either Oral, Intravenous, or Both");
            return;
        }

        double mg;
        double ml;

        try
        {
            mg = Double.parseDouble(editMg.getText().toString());
            ml = Double.parseDouble(editMl.getText().toString());
        }
        catch(Exception ex)
        {
            Helper.toast(mActivity, "Milligrams and Millilitres must be whole numbers or decimal values");
            return;
        }

        short drugType;
        if(radioIV.isChecked())
            drugType = DrugType.INTRAVENOUS;
        else if(radioOral.isChecked())
            drugType = DrugType.ORAL;
        else
            drugType = DrugType.BOTH;

        mDrug.setMg(mg);
        mDrug.setMl(ml);
        mDrug.setName(editName.getText().toString());
        mDrug.setType(drugType);
        mDrug.setDangerous(checkDangerous.isChecked());
        DB db = DB.get(mActivity);

        if(mDrug.getId() == 0)
        {
            mDrug.setId(db.drug().insert(mDrug));
            mActivity.addDrug(mDrug);
        }
        else
        {
            db.drug().update(mDrug);
            mActivity.updateDrug(mDrug);
        }

        Helper.toast(mActivity, "Drug Saved Successfully");
        dismiss();
    }

    private void deleteDrug()
    {
        DB db = DB.get(mActivity);
        db.drug().delete(mDrug);
        mActivity.removeDrug(mDrug);
        dismiss();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonSave:
                saveDrug();
                break;
            case R.id.buttonClose:
                dismiss();
                break;
            case R.id.buttonDelete:
                deleteDrug();
                break;
        }
    }
}
