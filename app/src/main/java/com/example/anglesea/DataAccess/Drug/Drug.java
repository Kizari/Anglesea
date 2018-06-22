package com.example.anglesea.DataAccess.Drug;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Drug
{
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mg")
    private double mg;

    @ColumnInfo(name = "ml")
    private double ml;

    @ColumnInfo(name = "isRedDrug")
    private boolean isRedDrug;

    @ColumnInfo(name = "isIntravenous")
    private boolean isIntravenous;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRedDrug() {
        return isRedDrug;
    }

    public void setRedDrug(boolean redDrug) {
        isRedDrug = redDrug;
    }

    public boolean isIntravenous() {
        return isIntravenous;
    }

    public void setIntravenous(boolean intravenous) {
        isIntravenous = intravenous;
    }

    public double getMg() {
        return mg;
    }

    public void setMg(double mg) {
        this.mg = mg;
    }

    public double getMl() {
        return ml;
    }

    public void setMl(double ml) {
        this.ml = ml;
    }
}
