package com.example.anglesea.Entities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity
{
    protected Helper mHelper;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mHelper = new Helper(this);
        mContext = this;
    }
}