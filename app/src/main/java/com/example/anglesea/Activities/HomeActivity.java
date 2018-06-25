package com.example.anglesea.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class HomeActivity extends BaseActivity
{
    String mRN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRN = getIntent().getExtras().getString("rn");

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

        int max = numberOfRooms - (numberOfRooms % roomsPerRow);

        int count = 1;
        int i;
        for(i = 1; i <= max; i++)
        {
            // Create cell
            Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonDoor), null, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 2;
            params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
            button.setLayoutParams(params);
            button.setText(prefix + i);

            final String roomNo = prefix + i;
            final Context context = mContext;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RoomActivity.class);
                    intent.putExtra("mRoom", roomNo);
                    intent.putExtra("rn", mRN);
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

                // Reset the counter
                count = 1;
            }
        }

        if((numberOfRooms % roomsPerRow) > 0)
        {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
            tv.setLayoutParams(params);
            row.addView(tv);

            for (i = max + 1; i <= numberOfRooms; i++)
            {
                // Create cell
                Button button = new Button(new ContextThemeWrapper(this, R.style.ButtonDoor), null, 0);
                params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 2;
                params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
                button.setLayoutParams(params);
                button.setText(prefix + i);

                final String roomNo = prefix + i;
                final Context context = mContext;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, RoomActivity.class);
                        intent.putExtra("mRoom", roomNo);
                        context.startActivity(intent);
                    }
                });

                // Add the cell to the current row
                row.addView(button);
            }

            tv = new TextView(this);
            params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            params.setMargins(mHelper.px(5), mHelper.px(5), mHelper.px(5), mHelper.px(5));
            tv.setLayoutParams(params);
            row.addView(tv);

            container.addView(row);
        }
    }
}
