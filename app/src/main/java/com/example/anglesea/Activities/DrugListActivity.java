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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

import java.util.ArrayList;
import java.util.List;

public class DrugListActivity extends BaseActivity {

    private static final String TAG ="ListDatActivity";
    private ListView Lview;
    private EditText drugId;
    private FloatingActionButton drugListButton;

    //  private floatingActionButton addDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);

        Lview = (ListView) findViewById(R.id.drugList);
        drugListButton = (FloatingActionButton) findViewById(R.id.drugListButton);

        populateListView();
    }//End of On Create

    private void populateListView(){
        Log.d(TAG,"populateListView: Display list of drugs");
        //Retrieve list from database and populate list view
        List<Drug> listDrugs = mDatabase.drug().getAll();

        //Create the list adapter and set
        ListAdapter adapter = new DrugListAdapter(this, R.layout.item_drug, listDrugs);
        Lview.setAdapter(adapter);

        //Set an onItemListener to the ListView
        Lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Drug drug = (Drug)adapterView.getItemAtPosition(i);
                Log.d(TAG,"onItemClick: You Clicked on " + drug.getName());
                Intent edit = new Intent(DrugListActivity.this,AddDrugActivity.class);
                edit.putExtra("Id", drug.getId());
                edit.putExtra("drugName", drug.getName());
                startActivity(edit);
            }
        });



    }//End of Populate View



    public void listOnClick(View view){
        Intent i = new Intent(this,AddDrugActivity.class);
        startActivity(i);
    }

    private class DrugListAdapter extends ArrayAdapter<Drug>
    {
        public DrugListAdapter(Context context, int resource, List<Drug> items)
        {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_drug, parent);

            TextView name = view.findViewById(R.id.textName);
            TextView strength = view.findViewById(R.id.textStrength);

            Drug drug = getItem(position);

            name.setText(drug.getName());
            strength.setText(drug.getStrength());

            return view;
        }
    }
}
