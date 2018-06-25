package com.example.anglesea.DataAccess.Chart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Chart
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "NHI")
    private String NHI;

    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNHI() {
        return NHI;
    }

    public void setNHI(String NHI) {
        this.NHI = NHI;
    }
}
