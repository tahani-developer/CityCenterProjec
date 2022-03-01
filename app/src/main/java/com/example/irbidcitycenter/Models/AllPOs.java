package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "AllPOsTABLE")
public class AllPOs {
    @PrimaryKey(autoGenerate = true)
    public int SERIAL;
    @ColumnInfo(name = "PoNum")
    public String PoNum;

    public int getSERIAL() {
        return this.SERIAL;
    }

    public void setSERIAL(final int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getPoNum() {
        return this.PoNum;
    }

    public void setPoNum(final String poNum) {
        this.PoNum = poNum;
    }
}
