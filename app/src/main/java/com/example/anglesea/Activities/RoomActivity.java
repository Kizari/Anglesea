package com.example.anglesea.Activities;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.DataAccess.Room.Room;
import com.example.anglesea.Dialogs.AddOrEditDrugDialog;
import com.example.anglesea.Dialogs.AddOrEditPatientDialog;
import com.example.anglesea.Dialogs.PrecalculationDialog;
import com.example.anglesea.Dialogs.SummaryDialog;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.DrugType;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RoomActivity extends BaseActivity
{
    public Room mRoom;
    public Patient mPatient;
    public String mRN;

    public boolean mIsEditMode;

    CardView cardNew, cardExisting;
    TextView textFullName, textNHI, textAge;
    Button buttonOral, buttonIntravenous, buttonEdit;
    LinearLayout layoutEditBackground;

    private ListView drugList;
    private short drugType;

    private List<Drug> mDrugs;
    private DrugListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mRN = getIntent().getExtras().getString("rn");
        mRoom = mDatabase.room().getByName(getIntent().getStringExtra("mRoom"));
        mPatient = mDatabase.patient().getByRoom(mRoom.getId());
        mIsEditMode = false;

        SpannableString s = new SpannableString(mRoom.getRoomName() + " Details");
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        textFullName = findViewById(R.id.textFullName);
        textNHI = findViewById(R.id.textNHI);
        textAge = findViewById(R.id.textAge);

        cardNew = findViewById(R.id.cardNew);
        cardExisting = findViewById(R.id.cardExisting);

        buttonOral = findViewById(R.id.buttonOral);
        buttonIntravenous = findViewById(R.id.buttonIntravenous);
        buttonEdit = findViewById(R.id.buttonEditMode);
        layoutEditBackground = findViewById(R.id.layoutEditBackground);

        if(mPatient != null)
        {
            cardNew.setVisibility(View.GONE);
            textFullName.setText(mPatient.getFullName());
            textNHI.setText(mPatient.getNHI());
            textAge.setText(String.valueOf(mHelper.calculateAge(mPatient.getDOB())) + " Years");
        }
        else
        {
            cardExisting.setVisibility(View.GONE);
        }

        drugType = getIntent().getExtras().getShort("drugType");
        drugType = DrugType.ORAL;
        String title;

        drugList = findViewById(R.id.drugList);
        mDrugs = mDatabase.drug().getAllByType(drugType);
        sortDrugList();
        mAdapter = new DrugListAdapter(this, mDrugs);
        drugList.setAdapter(mAdapter);
    }

    public void showPatientDetails(Patient p)
    {
        mPatient = p;
        textFullName.setText(mPatient.getFullName());
        textNHI.setText(mPatient.getNHI());
        textAge.setText(String.valueOf(mHelper.calculateAge(mPatient.getDOB())) + " Years");
        cardNew.setVisibility(View.GONE);
        cardExisting.setVisibility(View.VISIBLE);
    }

    public void hidePatientDetails()
    {
        mPatient = null;
        cardNew.setVisibility(View.VISIBLE);
        cardExisting.setVisibility(View.GONE);
    }

    public void addDrug(View v)
    {
        AddOrEditDrugDialog.Create((RoomActivity)this, null, false);
    }

    public void removeDrug(Drug drug)
    {
        if(mDrugs.contains(drug))
        {
            mDrugs.remove(drug);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void addDrug(Drug drug)
    {
        if(drug.getType() == drugType)
        {
            mDrugs.add(drug);
            sortDrugList();
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateDrug(Drug drug)
    {
        if(drug.getType() != DrugType.BOTH && drug.getType() != drugType)
        {
            Drug temp = null;
            for(Drug d : mDrugs)
            {
                if(d.getId() == drug.getId())
                    temp = d;
            }

            if(temp != null)
                mDrugs.remove(temp);
        }

        sortDrugList();
        mAdapter.notifyDataSetChanged();
    }

    public void onNewPatient(View v)
    {
        AddOrEditPatientDialog.Create(this, false);
    }

    public void onEditPatient(View v)
    {
        AddOrEditPatientDialog.Create(this, true);
    }

    public void onEditMode(View v)
    {
        mIsEditMode = !mIsEditMode;

        if(mIsEditMode)
        {
            layoutEditBackground.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            buttonEdit.getBackground().setColorFilter(getResources().getColor(R.color.textLight), PorterDuff.Mode.SRC_ATOP);
        }
        else
        {
            layoutEditBackground.setBackgroundColor(getResources().getColor(R.color.colorShadow));
            buttonEdit.getBackground().setColorFilter(getResources().getColor(R.color.mainText), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void switchDrugList(short type)
    {
        drugType = type;

        mDrugs.clear();
        mDrugs.addAll(mDatabase.drug().getAllByType(drugType));
        sortDrugList();
        mAdapter.notifyDataSetChanged();
    }

    public void onOralClicked(View v)
    {
        buttonOral.setBackgroundColor(getResources().getColor(android.R.color.white));
        buttonOral.setTextColor(getResources().getColor(R.color.colorPrimary));

        buttonIntravenous.setBackgroundColor(getResources().getColor(R.color.colorShadow));
        buttonIntravenous.setTextColor(getResources().getColor(R.color.mainText));

        switchDrugList(DrugType.ORAL);
    }

    public void onIntravenousClicked(View v)
    {
        buttonIntravenous.setBackgroundColor(getResources().getColor(android.R.color.white));
        buttonIntravenous.setTextColor(getResources().getColor(R.color.colorPrimary));

        buttonOral.setBackgroundColor(getResources().getColor(R.color.colorShadow));
        buttonOral.setTextColor(getResources().getColor(R.color.mainText));

        switchDrugList(DrugType.INTRAVENOUS);
    }

    private void sortDrugList()
    {
        Collections.sort(mDrugs, new Comparator<Drug>()
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
    }

    public void onDischarge(View v)
    {
        SummaryDialog.Create(this, mPatient.getChartId());
        mPatient.setRoomId(-1);
        mDatabase.patient().update(mPatient);
        hidePatientDetails();
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
            TextView administered = convertView.findViewById(R.id.textAdministered);
            LinearLayout layout = convertView.findViewById(R.id.layout);
            //Button edit = convertView.findViewById(R.id.editButton);
            ImageView imageDangerous = convertView.findViewById(R.id.imageDangerous);

            final Drug drug = getItem(position);

            if(!drug.isDangerous())
                imageDangerous.setVisibility(View.GONE);
            else
                imageDangerous.setVisibility(View.VISIBLE);

            if(mPatient != null)
            {
                long lastAdministered = mDatabase.administration().getMaxDateForDrug(mPatient.getChartId(), drug.getId());

                if(lastAdministered > 0)
                {
                    DateFormat df = new SimpleDateFormat("d MMM 'at' h:mma");
                    administered.setText("Last administered " + df.format(lastAdministered));
                }
                else
                {
                    administered.setText("No administrations this visit");
                }
            }
            else
            {
                administered.setText("No administrations this visit");
            }

            name.setText(drug.getName());
            String strengthString = Helper.format(drug.getMg()) + "mg / " + Helper.format(drug.getMl()) + "ml";
            strength.setText(strengthString);

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(mIsEditMode)
                    {
                        AddOrEditDrugDialog.Create((RoomActivity) mContext, drug, drug.isDangerous());
                    }
                    else
                    {
                        if(mPatient != null)
                            PrecalculationDialog.Create((RoomActivity) mContext, drug.getId());
                    }
                }
            });

            return convertView;
        }
    }
}
