package com.example.anglesea.Entities;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.TypedValue;

import java.util.Calendar;

public class Helper
{
    private static final String DATABASE_NAME = "DrugList";
    private static final String TABLE_NAME = "drug_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "DRUGNAME ";
    private Context mContext;

    public Helper(Context context)
    {
        mContext = context;
    }

    public int px(float dp)
    {
        Resources r = mContext.getResources();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public int calculateAge(long dob)
    {
        Calendar now = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(dob);

        int year1 = now.get(Calendar.YEAR);
        int year2 = birth.get(Calendar.YEAR);

        int age = year1 - year2;

        int month1 = now.get(Calendar.DAY_OF_YEAR);
        int month2 = birth.get(Calendar.DAY_OF_YEAR);
        if (month2 > month1)
        {
            age--;
        }

        return age;
    }
}
