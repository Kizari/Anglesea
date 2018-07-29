package com.example.anglesea.Entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.example.anglesea.R;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AuditAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, String, Audit, Object>
{
    /*@Override
    public int getItemCount()
    {
        return item
    }*/
    Context mContext;

    public AuditAdapter(Context context)
    {
        mContext = context;
    }

    @Override
    public AuditHolder onCreateItemViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_audit, viewGroup, false);
        AuditHolder holder = new AuditHolder(v);
        return holder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType)
    {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_audit, parent, false);
        return new AuditHeaderHolder(headerView);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i)
    {
        AuditHolder holder = (AuditHolder)viewHolder;
        Audit audit = getItem(i);

        DateFormat df = new SimpleDateFormat("EEEE dd MMM yyyy");
        holder.textDate.setText(df.format(audit.timestamp));

        df = new SimpleDateFormat("h:mma");
        holder.textTime.setText(df.format(audit.timestamp));

        holder.textMillilitres.setText(Math.round(audit.millilitres) + "ml");
        holder.textNurseName.setText("Verified by " + audit.nurse.getFullName());
        holder.textPatientName.setText(audit.patient.getFullName());
        holder.textDrugName.setText(audit.drug.getName());
        holder.textNHI.setText(audit.patient.getNHI());
        holder.textRN.setText(audit.nurse.getRN());
        holder.textRoom.setText("Room " + audit.room.getRoomName());

        //Sharp.loadString(audit.signature).into(holder.imageSignature);

        SVG svg;
        try
        {
            svg = SVG.getFromString(audit.signature);
        }
        catch(Exception ex)
        {
            svg = null;
        }

        if(svg != null && svg.getDocumentWidth() != -1)
        {
            Bitmap bm = Bitmap.createBitmap((int)Math.ceil(svg.getDocumentWidth()),
                    (int)Math.ceil(svg.getDocumentHeight()),
                    Bitmap.Config.ARGB_8888);

            Canvas bmc = new Canvas(bm);
            bmc.drawRGB(255, 255, 255);
            svg.renderToCanvas(bmc);
            holder.imageSignature.setImageDrawable(new BitmapDrawable(mContext.getResources(), bm));
        }
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        String chartId = getHeader();
        AuditHeaderHolder headerHolder = (AuditHeaderHolder)holder;
        headerHolder.textChartId.setText(chartId);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class AuditHolder extends RecyclerView.ViewHolder
    {
        TextView textPatientName, textNHI, textNurseName, textRN, textMillilitres, textDrugName, textRoom, textTime, textDate;
        ImageView imageSignature;

        AuditHolder(View itemView)
        {
            super(itemView);
            textPatientName = itemView.findViewById(R.id.textPatientName);
            textNHI = itemView.findViewById(R.id.textNHI);
            textNurseName = itemView.findViewById(R.id.textNurse);
            textRN = itemView.findViewById(R.id.textRN);
            textMillilitres = itemView.findViewById(R.id.textDrugMillilitres);
            textDrugName = itemView.findViewById(R.id.textDrugName);
            textRoom = itemView.findViewById(R.id.textRoom);
            textTime = itemView.findViewById(R.id.textTime);
            textDate = itemView.findViewById(R.id.textDate);
            imageSignature = itemView.findViewById(R.id.imageSignature);
        }
    }

    public static class AuditHeaderHolder extends RecyclerView.ViewHolder
    {
        TextView textChartId;

        AuditHeaderHolder(View itemView)
        {
            super(itemView);
            textChartId = itemView.findViewById(R.id.textChartId);
        }
    }
}