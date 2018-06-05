package com.example.anglesea.Entities;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class Helper
{
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
