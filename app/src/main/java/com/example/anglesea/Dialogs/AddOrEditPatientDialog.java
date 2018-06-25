package com.example.anglesea.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anglesea.Activities.RoomActivity;
import com.example.anglesea.DataAccess.Chart.Chart;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.Entities.BaseDialog;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddOrEditPatientDialog extends BaseDialog implements View.OnClickListener
{
    LinearLayout layoutConditional, layoutButtons;
    Button buttonCheck, buttonSave, buttonDelete, buttonClose;
    TextView textTitle;
    EditText editName, editNHI, editDay, editMonth, editYear;

    boolean isEditMode;
    boolean isNew;

    Patient mPatient;
    RoomActivity mActivity;

    private AddOrEditPatientDialog(RoomActivity activity, boolean editMode)
    {
        super(activity);
        mActivity = activity;
        isEditMode = editMode;
    }

    public static void Create(RoomActivity activity, boolean isEditMode)
    {
        AddOrEditPatientDialog dialog = new AddOrEditPatientDialog(activity, isEditMode);
        dialog.showFull(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addoredit_patient);

        buttonClose = findViewById(R.id.buttonClose);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCheck = findViewById(R.id.buttonCheck);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonClose.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        buttonCheck.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editDay = findViewById(R.id.editDay);
        editMonth = findViewById(R.id.editMonth);
        editYear = findViewById(R.id.editYear);

        editName = findViewById(R.id.editName);
        editNHI = findViewById(R.id.editNHI);

        textTitle = findViewById(R.id.textTitle);

        layoutConditional = findViewById(R.id.layoutConditional);
        layoutButtons = findViewById(R.id.layoutButtons);

        if(!isEditMode)
        {
            layoutConditional.setVisibility(View.GONE);
            layoutButtons.setVisibility(View.GONE);
        }
        else
        {
            buttonCheck.setVisibility(View.GONE);
            isNew = false;
            textTitle.setText("Edit Patient");

            mPatient = mActivity.mPatient;
            editName.setText(mPatient.getFullName());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mPatient.getDOB());

            editNHI.setText(mPatient.getNHI());
            editDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            editMonth.setText(String.valueOf(calendar.get(Calendar.MONTH)));
            editYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        }
    }

    private void checkPatient()
    {
        if(editNHI.getText().toString().equals(""))
        {
            Helper.toast(mContext, "Please enter an NHI number");
            return;
        }

        Patient patient = mDatabase.patient().getByNHI(editNHI.getText().toString().toUpperCase());

        if(patient == null)
        {
            isNew = true;
            textTitle.setText("New Patient");
            mPatient = new Patient();
        }
        else
        {
            Chart chart = new Chart();
            chart.setNHI(patient.getNHI());
            long chartId = mDatabase.chart().insert(chart);

            patient.setChartId(chartId);
            patient.setRoomId(mActivity.mRoom.getId());
            mDatabase.patient().update(patient);
            mActivity.showPatientDetails(patient);
            dismiss();
            return;
        }

        editNHI.setEnabled(false);
        layoutConditional.setVisibility(View.VISIBLE);
        buttonCheck.setVisibility(View.GONE);
        layoutButtons.setVisibility(View.VISIBLE);
    }

    private void deletePatient()
    {
        Patient patient = mDatabase.patient().getByNHI(editNHI.getText().toString());

        if(patient != null)
            mDatabase.patient().delete(patient);

        mActivity.hidePatientDetails();
        dismiss();
    }

    private void savePatient()
    {
        if(editName.getText().toString().equals("") ||
                editNHI.getText().toString().equals("") ||
                editDay.getText().toString().equals("") ||
                editMonth.getText().toString().equals("") ||
                editYear.getText().toString().equals(("")))
        {
            Helper.toast(mContext, "Please ensure all fields are filled out");
            return;
        }

        short day;
        short month;
        short year;

        try
        {
            day = Short.parseShort(editDay.getText().toString());
            month = Short.parseShort(editMonth.getText().toString());
            year = Short.parseShort(editYear.getText().toString());
        }
        catch(Exception ex)
        {
            Helper.toast(mContext, "Please ensure all date values are numeric");
            return;
        }

        mPatient.setNHI(editNHI.getText().toString().toUpperCase());
        mPatient.setRoomId(mActivity.mRoom.getId());
        mPatient.setDOB(new GregorianCalendar(year, month, day).getTimeInMillis());
        mPatient.setFullName(editName.getText().toString());

        Chart chart = new Chart();
        chart.setNHI(mPatient.getNHI());
        long chartId = mDatabase.chart().insert(chart);

        mPatient.setChartId(chartId);

        if(isNew)
            mDatabase.patient().insert(mPatient);
        else
            mDatabase.patient().update(mPatient);

        mActivity.showPatientDetails(mPatient);

        dismiss();
    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.buttonCheck:
                checkPatient();
                break;
            case R.id.buttonSave:
                savePatient();
                break;
            case R.id.buttonDelete:
                deletePatient();
                break;
            case R.id.buttonClose:
                dismiss();
                break;
        }
    }
}
