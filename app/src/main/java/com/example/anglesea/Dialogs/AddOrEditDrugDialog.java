package com.example.anglesea.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anglesea.Activities.DrugListActivity;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.util.List;

public class AddOrEditDrugDialog extends Dialog implements View.OnClickListener
{
    public static AddOrEditDrugDialog Create(DrugListActivity activity, Drug drug, boolean isDangerous)
    {
        AddOrEditDrugDialog dialog = new AddOrEditDrugDialog((DrugListActivity)activity, drug, isDangerous);
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return dialog;
    }

    private DrugListActivity mActivity;
    private Drug mDrug;
    private boolean mIsDangerous;

    EditText editMg, editMl, editName;
    Button buttonSave, buttonClose, buttonDelete;
    RadioButton radioOral, radioIV;

    private AddOrEditDrugDialog(DrugListActivity activity, Drug drug, boolean isDangerous)
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
        setContentView(R.layout.activity_add_drug);

        buttonSave = findViewById(R.id.buttonSave);
        buttonClose = findViewById(R.id.buttonClose);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(this);
        buttonClose.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        radioOral = findViewById(R.id.radioOral);
        radioIV = findViewById(R.id.radioIV);

        TextView textTitle = findViewById(R.id.textTitle);
        editMg = findViewById(R.id.editMg);
        editMl = findViewById(R.id.editMl);
        editName = findViewById(R.id.editName);

        if(mDrug == null)
        {
            textTitle.setText("New Drug");
            mDrug = new Drug();
            mDrug.setRedDrug(mIsDangerous);
        }
        else
        {
            textTitle.setText("Editing Drug");
            editName.setText(mDrug.getName());
            editMg.setText(Helper.format(mDrug.getMg()));
            editMl.setText(Helper.format(mDrug.getMl()));

            if(mDrug.isIntravenous())
                radioIV.toggle();
            else
                radioOral.toggle();
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
            Helper.toast(mActivity, "Please select either Oral or Intravenous");
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

        mDrug.setMg(mg);
        mDrug.setMl(ml);
        mDrug.setName(editName.getText().toString());
        mDrug.setIntravenous(radioIV.isChecked());
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
