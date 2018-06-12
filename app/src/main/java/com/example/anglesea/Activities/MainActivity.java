package com.example.anglesea.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.R;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SystemClock.sleep(2000);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRoomListActivity(View v)
    {
        Intent intent = new Intent(this, RoomListActivity.class);
        startActivity(intent);
    }

    public void startAddDrug(View v)
    {
        Intent intent = new Intent(this, AddDrugActivity.class);
        startActivity(intent);
    }

    public void startDrugListScreen(View v)
    {
        Intent intent = new Intent(this, DrugListActivity.class);
        startActivity(intent);
    }
}
