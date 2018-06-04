package com.example.anglesea;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
