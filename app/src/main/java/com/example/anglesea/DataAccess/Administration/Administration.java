package com.example.anglesea.DataAccess.Administration;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Administration
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "NHI")
    private String NHI;

    @ColumnInfo(name = "drugId")
    private long drugId;

    @ColumnInfo(name = "quantity")
    private double quantity;

    public long getId() {
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
}
