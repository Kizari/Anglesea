package com.example.anglesea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

public class DrugListScreen extends AppCompatActivity {

    private static final String TAG ="ListDatActivity";
    private ListView Lview;
    private EditText drugId;
    private Button drugListButton;
    Helper myDb;
    //  private floatingActionButton addDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list_screen);

        Lview = (ListView) findViewById(R.id.drugList);
        drugListButton = (Button) findViewById(R.id.drugListButton);

        populateListView();
    }//End of On Create

    private void populateListView(){
        Log.d(TAG,"populateListView: Display list of drugs");
        //Retrieve list from database and populate list view
        Cursor data = myDb.showList();
        ArrayList<String> listDrugs = new ArrayList<>();
        while (data.moveToNext()){
            listDrugs.add(data.getString(1));
        }

        //Create the list adapter and set
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listDrugs);
        Lview.setAdapter(adapter);

        //Set an onItemListener to the ListView
        Lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String drugName = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG,"onItemClick: You Clicked on " + drugName);
                Cursor data = myDb.getDrugId(drugName);
                int itemId = -1;
                while (data.moveToNext()){
                    itemId = data.getInt(0);
                }
                if (itemId > - 1){
                    Log.d(TAG,"onItemClick: Drug Id: " + itemId);
                    Intent edit = new Intent(DrugListScreen.this,AddDrug.class);
                    edit.putExtra("Id", itemId);
                    edit.putExtra("drugName", drugName);
                    startActivity(edit);
                }else{
                    Toast.makeText(DrugListScreen.this,"No drug of that name", Toast.LENGTH_LONG).show();
                }
            }
        });



    }//End of Populate View



    public void listOnClick(View view){
        Intent i = new Intent(this,AddDrug.class);
        startActivity(i);
    }

}
