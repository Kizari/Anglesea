package com.example.anglesea.Activities;

import android.content.Context;
import android.os.Bundle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class DrugListActivity extends BaseActivity {

    private static final String TAG ="ListDatActivity";
    private ListView Lview, drugList;
    private EditText drugId;
    private FloatingActionButton drugListButton;

    //  private floatingActionButton addDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);

        populateDefaultDrugs();

        Lview = (ListView) findViewById(R.id.drugList);
        drugListButton = (FloatingActionButton) findViewById(R.id.drugListButton);

        populateListView();
    }//End of On Create

    private void populateDefaultDrugs()
    {
        if(mDatabase.drug().getAll().size() <= 0)
        {
            Drug drug = new Drug();
            drug.setName("Morphine Syrup");
            drug.setRedDrug(true);
            drug.setStrength(100);
            mDatabase.drug().insert(drug);

            Drug drug1 = new Drug();
            drug1.setName("Paracetamol Syrup");
            drug1.setStrength(250);
            drug1.setRedDrug(false);
            mDatabase.drug().insert(drug1);

            Drug drug2 = new Drug();
            drug2.setName("Paracetamol Syrup");
            drug2.setStrength(120);
            drug2.setRedDrug(false);
            mDatabase.drug().insert(drug2);

            Drug drug3 = new Drug();
            drug3.setName("Ibupofen Syrup");
            drug3.setStrength(100);
            drug3.setRedDrug(false);
            mDatabase.drug().insert(drug3);

            Drug drug4 = new Drug();
            drug4.setName("Cyclizine Syrup");
            drug4.setStrength(50);
            drug4.setRedDrug(false);
            mDatabase.drug().insert(drug4);

            Drug drug5 = new Drug();
            drug5.setName("Droperidol Syrup");
            drug5.setStrength(2.5f);
            drug5.setRedDrug(false);
            mDatabase.drug().insert(drug5);

        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ListAdapter adapter = new DrugListAdapter(this, mDatabase.drug().getAll());
        Lview.setAdapter(adapter);
    }

    private void populateListView(){
        Log.d(TAG,"populateListView: Display list of drugs");
        //Retrieve list from database and populate list view
        List<Drug> listDrugs = mDatabase.drug().getAll();

        //Create the list adapter and set
        ListAdapter adapter = new DrugListAdapter(this, listDrugs);
        Lview.setAdapter(adapter);

        //Set an onItemListener to the ListView
        Lview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Drug drug = (Drug)adapterView.getItemAtPosition(i);
                Log.d(TAG,"onItemClick: You Clicked on " + drug.getName());
                Intent edit = new Intent(DrugListActivity.this,AddDrugActivity.class);
                edit.putExtra("Id", drug.getId());
                edit.putExtra("drugName", drug.getName());
                startActivity(edit);
            }
        }); 
    }//End of Populate View



    public void listOnClick(View view)
    {
        Intent i = new Intent(this,AddDrugActivity.class);
        startActivity(i);
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
            Button delete = convertView.findViewById(R.id.deleteButton);

            final Drug drug = getItem(position);

            name.setText(drug.getName());
            strength.setText(valueOf(drug.getStrength()));

            delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mDatabase.drug().delete(drug);
                    onResume();
                }
            });

            if(drug.isRedDrug())
                layout.setBackgroundColor(getResources().getColor(R.color.redDrug));

            return convertView;
        }
    }
}
