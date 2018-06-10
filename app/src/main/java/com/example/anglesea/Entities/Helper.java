package com.example.anglesea.Entities;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.TypedValue;

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
}
