package com.example.anglesea.Activities;

import android.os.Bundle;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;

import com.example.anglesea.DataAccess.Drug.Drug;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

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
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listDrugs);
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

}
