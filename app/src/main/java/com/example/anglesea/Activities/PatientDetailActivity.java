package com.example.anglesea.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Dialogs.AddOrEditDrugDialog;
import com.example.anglesea.Dialogs.DrugTypeDialog;
import com.example.anglesea.Dialogs.PrecalculationDialog;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.DrugType;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

public class PatientDetailActivity extends BaseActivity
{
    Room room;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        room = mDatabase.room().getByName(getIntent().getStringExtra("room"));
        patient = mDatabase.patient().getByRoom(room.getId());

        SpannableString s = new SpannableString(room.getRoomName() + " Details");
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        TextView textFullName = findViewById(R.id.textFullName);
        TextView textNHI = findViewById(R.id.textNHI);
        TextView textAge = findViewById(R.id.textAge);

        textFullName.setText(patient.getFullName());
        textNHI.setText(patient.getNHI());
        textAge.setText(String.valueOf(mHelper.calculateAge(patient.getDOB())) + " Years");


        drugType = getIntent().getExtras().getShort("drugType");
        drugType = DrugType.ORAL;
        String title;


        safeList = findViewById(R.id.drugList);
        //dangerousList = findViewById(R.id.dangerousDrugList);

        //drugListButton = (FloatingActionButton) findViewById(R.id.drugListButton);

        populateListView();
    }

    private static final String TAG ="ListDatActivity";
    private ListView safeList, dangerousList;
    private EditText drugId;
    private short drugType;

    private List<Drug> mSafeDrugs;
    private List<Drug> mDangerousDrugs;

    private DrugListAdapter mSafeAdapter;
    private DrugListAdapter mDangerousAdapter;

    private void populateListView()
    {
        Log.d(TAG,"populateListView: Display list of drugs");

        //Retrieve list from database and populate list view
        mSafeDrugs = mDatabase.drug().getAllByType(drugType);
        mDangerousDrugs = mDatabase.drug().getAllDangerous(drugType);

        Collections.sort(mSafeDrugs, new Comparator<Drug>()
        {
            @Override
            public int compare(Drug drug, Drug t1)
            {
                if(drug.isDangerous() == t1.isDangerous())
                {
                    return drug.getName().compareTo(t1.getName());
                }
                else
                {
                    if(drug.isDangerous() && !t1.isDangerous())
                        return 1;
                    else if(!drug.isDangerous() && t1.isDangerous())
                        return -1;
                }

                return 0;
            }
        });

        //Create the list adapter and set
        mSafeAdapter = new DrugListAdapter(this, mSafeDrugs);
        mDangerousAdapter = new DrugListAdapter(this, mDangerousDrugs);

        safeList.setAdapter(mSafeAdapter);
        //dangerousList.setAdapter(mDangerousAdapter);
    }//End of Populate View

    public void addSafeDrug(View v)
    {
        AddOrEditDrugDialog.Create((PatientDetailActivity)this, null, false);
    }

    public void addDangerousDrug(View v)
    {
        AddOrEditDrugDialog.Create((PatientDetailActivity)this, null, true);
    }

    public void removeDrug(Drug drug)
    {
        if(drug.isDangerous())
        {
            mDangerousDrugs.remove(drug);
            mDangerousAdapter.notifyDataSetChanged();
        }
        else
        {
            mSafeDrugs.remove(drug);
            mSafeAdapter.notifyDataSetChanged();
        }
    }

    public void addDrug(Drug drug)
    {
        if(drug.isDangerous())
        {
            mDangerousDrugs.add(drug);
            mDangerousAdapter.notifyDataSetChanged();
        }
        else
        {
            mSafeDrugs.add(drug);
            mSafeAdapter.notifyDataSetChanged();
        }
    }

    public void updateDrug(Drug drug)
    {
        if(drug.isDangerous())
            mDangerousAdapter.notifyDataSetChanged();
        else
            mSafeAdapter.notifyDataSetChanged();
    }

    private class DrugListAdapter extends ArrayAdapter<Drug>
    {
        public DrugListAdapter(Context context, List<Drug> items)
        {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drug, parent, false);

            TextView name = convertView.findViewById(R.id.textName);
            TextView strength = convertView.findViewById(R.id.textStrength);
            LinearLayout layout = convertView.findViewById(R.id.layout);
            //Button edit = convertView.findViewById(R.id.editButton);
            ImageView imageDangerous = convertView.findViewById(R.id.imageDangerous);

            final Drug drug = getItem(position);

            if(!drug.isDangerous())
                imageDangerous.setVisibility(View.GONE);
            else
                imageDangerous.setVisibility(View.VISIBLE);

            name.setText(drug.getName());
            String strengthString = Helper.format(drug.getMg()) + " mg / " + Helper.format(drug.getMl()) + "ml";
            strength.setText(strengthString);

            /*edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AddOrEditDrugDialog.Create((DrugListActivity)mContext, drug, drug.isDangerous());
                }
            });*/

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    AddOrEditDrugDialog.Create((PatientDetailActivity)mContext, drug, drug.isDangerous());
                    //PrecalculationDialog.Create(mContext, drug.getId());
                }
            });

            return convertView;
        }
    }
}
