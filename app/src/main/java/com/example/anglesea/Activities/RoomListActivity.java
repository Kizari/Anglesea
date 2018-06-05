package com.example.anglesea.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anglesea.DataAccess.DB;
import com.example.anglesea.DataAccess.Patient.Patient;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

import java.util.GregorianCalendar;

public class RoomListActivity extends BaseActivity
{
    private void databaseExample()
    {
        // Get reference to the database
        DB db = DB.get(this);

        // Create a new patient object
        Patient patient = new Patient();
        patient.setNHI("ABC1234");
        patient.setFullName("John Smith");
        patient.setDOB((new GregorianCalendar(1990, 1, 1).getTimeInMillis()));
        patient.setRoomId("Rm10");

        // Insert the patient into the database
        db.patient().insert(patient);

        // Select patient by room id
        Patient patient2 = db.patient().getByRoom("Rm10");

        // Delete the patient out of the database
        db.patient().delete(patient2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        createRooms((LinearLayout)findViewById(R.id.layoutDay), 12, 6, "D");
        createRooms((LinearLayout)findViewById(R.id.layoutNight), 17, 6, "Rm");
    }

    private void createRooms(LinearLayout container, int numberOfRooms, int roomsPerRow, String prefix)
    {
        LinearLayout row = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(lp);

        int count = 1;
        int i;
        for(i = 1; i <= numberOfRooms; i++)
        {
            // Create cell
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
            button.setLayoutParams(params);
            button.setBackground(getResources().getDrawable(R.drawable.room_button_background));
            button.setTextColor(getResources().getColor(R.color.colorAccent));
            button.setTextSize(16);
            button.setPadding(5, 5, 5, 5);
            button.setText(prefix + i);

            final Context context = mContext;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CalculationActivity.class);
                    context.startActivity(intent);
                }
            });

            // Add the cell to the current row
            row.addView(button);

            count++;

            if(count > roomsPerRow)
            {
                // Row is done, add to view
                container.addView(row);

                // Create the next row
                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(lp);

                // Reset the cell count
                count = 1;
            }
        }

        if(count > 1)
        {
            while(count <= roomsPerRow)
            {
                TextView tv = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1;
                params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
                tv.setLayoutParams(params);
                row.addView(tv);
                count++;
            }

            container.addView(row);
        }
    }
}
