package com.example.anglesea.DataAccess.Nurse;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by sarab on 6/12/2018.
 */

public class Nurse {
    @PrimaryKey
    @NonNull
    private String RN;

    @ColumnInfo(name = "FirstName")
    private String FirstName;

    @ColumnInfo(name = "LastName")
    private String LastName;

    @ColumnInfo(name = "Password")
    private String Password;

    public String getRN() {
        return RN;
    }

    public void setRN(String NHI) {
        this.RN = RN;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String fullName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPass(){return Password;}

    public void setPassword(String password){this.LastName = Password;}
}
