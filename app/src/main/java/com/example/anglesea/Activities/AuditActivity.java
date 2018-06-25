package com.example.anglesea.Activities;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anglesea.DataAccess.Administration.Administration;
import com.example.anglesea.Entities.Audit;
import com.example.anglesea.Entities.AuditAdapter;
import com.example.anglesea.Entities.BaseActivity;
import com.example.anglesea.Entities.Helper;
import com.example.anglesea.R;

import java.util.ArrayList;
import java.util.List;

public class AuditActivity extends BaseActivity
{
    LinearLayoutManager mManager;
    RecyclerView mRecycler;
    TextView textEmpty;
    EditText editChartNumber;
    Button buttonSearch;

    long mChartId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit);

        textEmpty = findViewById(R.id.textEmpty);
        editChartNumber = findViewById(R.id.editChartNumber);

        mRecycler = findViewById(R.id.recycler);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);

        if(getIntent().getExtras() != null)
        {
            LinearLayout searchBar = findViewById(R.id.layoutSearchBar);
            searchBar.setVisibility(View.GONE);

            mChartId = getIntent().getExtras().getLong("chartId");
            setList();
        }
        else
        {
            mRecycler.setVisibility(View.GONE);
        }
    }

    private void setList()
    {
        List<Administration> administrations = mDatabase.administration().getByChart(mChartId);

        if(administrations == null || administrations.size() < 1)
        {
            textEmpty.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.GONE);
            return;
        }

        textEmpty.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);

        ArrayList<Audit> audits = new ArrayList<>();
        for (Administration a : administrations) {
            audits.add(new Audit(a, this));
        }

        AuditAdapter adapter = new AuditAdapter();
        adapter.setHeader("Audit #1000" + mChartId);
        adapter.setItems(audits);
        mRecycler.setAdapter(adapter);
    }

    public void onSearch(View v)
    {
        try
        {
            long chartId = Long.parseLong(editChartNumber.getText().toString());
            mChartId = chartId - 10000;
        }
        catch(Exception ex)
        {
            Helper.toast(this, "Audit number must be numeric");
            return;
        }

        setList();
    }
}