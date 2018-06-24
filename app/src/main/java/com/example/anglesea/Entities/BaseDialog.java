package com.example.anglesea.Entities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.anglesea.DataAccess.DB;

public class BaseDialog extends Dialog
{
    protected Context mContext;
    protected DB mDatabase;

    protected BaseDialog(Context context)
    {
        super(context);
        mContext = context;
        mDatabase = DB.get(context);
    }

    protected void showFull(BaseDialog dialog)
    {
        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
