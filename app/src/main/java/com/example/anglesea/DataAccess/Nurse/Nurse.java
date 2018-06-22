package com.example.anglesea.DataAccess.Nurse;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by sarab on 6/12/2018.
 */

@Entity
public class Nurse
{
    public Nurse(@NonNull String RN, String FullName, String Password)
    {
        this.RN = RN;
        this.FullName = FullName;
        this.Password = Password;
    }

    @NonNull
    @PrimaryKey
    private String RN;

    @ColumnInfo(name = "FullName")
    private String FullName;

    @ColumnInfo(name = "Password")
    private String Password;

    @NonNull
    public String getRN() {
        return RN;
    }

    public void setRN(@NonNull String RN) {
        this.RN = RN;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
