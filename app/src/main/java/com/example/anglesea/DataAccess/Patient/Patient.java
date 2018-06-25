package com.example.anglesea.DataAccess.Patient;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Patient
{
    @PrimaryKey
    @NonNull
    private String NHI;

    @ColumnInfo(name = "fullName")
    private String fullName;

    @ColumnInfo(name = "DOB")
    private long DOB;

    @ColumnInfo(name = "roomId")
    private int roomId;

    @ColumnInfo(name = "chartId")
    private long chartId;

    public String getNHI() {
        return NHI;
    }

    public void setNHI(String NHI) {
        this.NHI = NHI;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDOB() {
        return DOB;
    }

    public void setDOB(long DOB) {
        this.DOB = DOB;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getChartId() {
        return chartId;
    }

    public void setChartId(long chartId) {
        this.chartId = chartId;
    }
}