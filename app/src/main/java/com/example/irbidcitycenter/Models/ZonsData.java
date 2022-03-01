package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ZonsDataTABLE")
public class ZonsData {
    @PrimaryKey(autoGenerate = true)
    public int SERIAL;

    @ColumnInfo(name = "ZONECODE")
    private   String zoneCode;
    @ColumnInfo(name = "ZONENAME")
    private  String ZONENAME;

    @ColumnInfo(name = "ZONETYPE")
    private  String ZONETYPE;

    public int getSERIAL() {

        return this.SERIAL;
    }

    public void setSERIAL(final int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getZoneCode() {
        return this.zoneCode;
    }

    public void setZoneCode(final String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZONENAME() {
        return this.ZONENAME;
    }

    public void setZONENAME(final String ZONENAME) {
        this.ZONENAME = ZONENAME;
    }

    public String getZONETYPE() {
        return this.ZONETYPE;
    }

    public void setZONETYPE(final String ZONETYPE) {
        this.ZONETYPE = ZONETYPE;
    }
}
