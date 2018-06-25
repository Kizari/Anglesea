package com.example.anglesea.DataAccess.Administration;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Administration
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "chartId")
    private long chartId;

    @ColumnInfo(name = "RN")
    private String RN;

    @ColumnInfo(name = "roomId")
    private int roomId;

    @ColumnInfo(name = "drugId")
    private long drugId;

    @ColumnInfo(name = "quantity")
    private double quantity;

    @ColumnInfo(name = "signature")
    private String signature;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDrugId() {
        return drugId;
    }

    public void setDrugId(long drugId) {
        this.drugId = drugId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
        this.chartId = chartId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
