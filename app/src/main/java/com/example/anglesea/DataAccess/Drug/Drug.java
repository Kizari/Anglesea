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

    @ColumnInfo(name = "strength")
    private int strength;

    @ColumnInfo(name = "isRedDrug")
    private boolean isRedDrug;

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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isRedDrug() {
        return isRedDrug;
    }

    public void setRedDrug(boolean redDrug) {
        isRedDrug = redDrug;
    }
}
