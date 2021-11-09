package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "STORE_TABLE")
public class Store {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    @ColumnInfo(name = "STORENO")
   String STORENO ;
    @ColumnInfo(name = "STORENAME")
    String     STORENAME;
    @ColumnInfo(name = "STOREKIND")
    String     STOREKIND;
    public Store() {
    }

    public String getSTOREKIND() {
        return STOREKIND;
    }

    public void setSTOREKIND(String STOREKIND) {
        this.STOREKIND = STOREKIND;
    }

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getSTORENO() {
        return STORENO;
    }

    public void setSTORENO(String STORENO) {
        this.STORENO = STORENO;
    }

    public String getSTORENAME() {
        return STORENAME;
    }

    public void setSTORENAME(String STORENAME) {
        this.STORENAME = STORENAME;
    }
}
