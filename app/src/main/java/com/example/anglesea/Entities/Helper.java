package com.example.anglesea.Entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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

    public static int calculateAge(long dob)
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

    public static String format(double d)
    {
        if(d == (long)d)
            return String.format("%d", (long)d);
        else
            return String.format("%s", d);
    }

    public static void toast(Context c, String message)
    {
        Toast.makeText(c, message, Toast.LENGTH_LONG).show();
    }

    private static final String PREFS_NAME = "AngleseaSharedPreferences";

    public static void saveStringPreference(Context c, String key, String value)
    {
        SharedPreferences.Editor editor = c.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String loadStringPreference(Context c, String key)
    {
        SharedPreferences prefs = c.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);

        if(restoredText != null)
        {
            return prefs.getString(key, "");
        }

        return "";
    }

    public static void appendPDFDeleteList(Context c, String file)
    {
        Gson gson = new Gson();
        String loaded = loadStringPreference(c, "deleteList");

        if(loaded.length() > 0)
        {
            ArrayList<String> list = gson.fromJson(loaded, ArrayList.class);
            list.add(file);
            saveStringPreference(c, "deleteList", gson.toJson(list));
        }
        else
        {
            ArrayList<String> list = new ArrayList<String>();
            list.add(file);
            saveStringPreference(c, "deleteList", gson.toJson(list));
        }
    }

    public static ArrayList<String> getPDFDeleteList(Context c)
    {
        Gson gson = new Gson();
        String loaded = loadStringPreference(c, "deleteList");

        if(loaded.length() > 0)
        {
            return gson.fromJson(loaded, ArrayList.class);
        }
        else
        {
            return new ArrayList<String>();
        }
    }

    public static void clearPDFDeleteList(Context c)
    {
        saveStringPreference(c, "deleteList", "");
    }
}
