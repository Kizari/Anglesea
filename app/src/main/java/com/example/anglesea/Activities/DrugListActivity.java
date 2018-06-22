package com.example.anglesea.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Dialogs.AddOrEditDrugDialog;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class DrugListActivity extends BaseActivity {

    private static final String TAG ="ListDatActivity";
    private ListView safeList, dangerousList;
    private EditText drugId;
    private boolean isIntravenous;

    private List<Drug> mSafeDrugs;
    private List<Drug> mDangerousDrugs;

    private DrugListAdapter mSafeAdapter;
    private DrugListAdapter mDangerousAdapter;


    //private FloatingActionButton drugListButton;

    //  private floatingActionButton addDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);

        isIntravenous = getIntent().getExtras().getBoolean("isIntravenous");
        String title;
        if(isIntravenous)
            title = "Intravenous Drugs";
        else
            title = "Oral Drugs";

        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan("Lato"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);

        safeList = findViewById(R.id.drugList);
        dangerousList = findViewById(R.id.dangerousDrugList);

        //drugListButton = (FloatingActionButton) findViewById(R.id.drugListButton);

        populateListView();
    }//End of On Create

    private void populateListView()
    {
        Log.d(TAG,"populateListView: Display list of drugs");

        //Retrieve list from database and populate list view
        mSafeDrugs = mDatabase.drug().getAllSafe(isIntravenous);
        mDangerousDrugs = mDatabase.drug().getAllDangerous(isIntravenous);

        //Create the list adapter and set
        mSafeAdapter = new DrugListAdapter(this, mSafeDrugs);
        mDangerousAdapter = new DrugListAdapter(this, mDangerousDrugs);

        safeList.setAdapter(mSafeAdapter);
        dangerousList.setAdapter(mDangerousAdapter);
    }//End of Populate View

    public void addSafeDrug(View v)
    {
        AddOrEditDrugDialog.Create((DrugListActivity)this, null, false);
    }

    public void addDangerousDrug(View v)
    {
        AddOrEditDrugDialog.Create((DrugListActivity)this, null, true);
    }

    public void removeDrug(Drug drug)
    {
        if(drug.isRedDrug())
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
        if(drug.isRedDrug())
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
        if(drug.isRedDrug())
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
            Button edit = convertView.findViewById(R.id.editButton);

            final Drug drug = getItem(position);

            name.setText(drug.getName());
            String strengthString = Helper.format(drug.getMg()) + " mg / " + Helper.format(drug.getMl()) + "ml";
            strength.setText(strengthString);

            edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AddOrEditDrugDialog.Create((DrugListActivity)mContext, drug, drug.isRedDrug());
                }
            });

            if(drug.isRedDrug())
            {
                name.setTextColor(getResources().getColor(R.color.redDrug));
                strength.setTextColor(getResources().getColor(R.color.redDrug));
            }

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(mContext, CalculationActivity.class);
                    intent.putExtra("drugId", drug.getId());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}
