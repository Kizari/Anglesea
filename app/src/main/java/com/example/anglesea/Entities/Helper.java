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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDrug(String drugName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, drugName);
        Log.d(TAG,"insertDrug: Drug Added " + drugName + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }
    public Cursor showList(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor dl = db.rawQuery(query, null);
        return dl;
    }
    public Cursor getDrugId(String drugName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query =" SELECT " + COL_1 + " FROM " + TABLE_NAME +
                " WHERE " + COL_2 + " + '" + drugName + "'";
        Cursor  dl = db.rawQuery(query, null);
        return dl;
    }
}
