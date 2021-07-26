package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "ZONELOGSTABLE")

public class ZoneLogs {
    @PrimaryKey(autoGenerate = true)
    public int SERIALLogs;

    @ColumnInfo(name = "ZONECODE")
    private   String zoneCode;

    @ColumnInfo(name = "ITEMCODE")
    private   String itemCode;


    @ColumnInfo(name = "UserNO")
    String UserNO;

    @ColumnInfo(name = "Newqty")
    private   String Newqty;



    @ColumnInfo(name = "LogsDATE")
    private   String LogsDATE;

    @ColumnInfo(name = "LogsTIME")
    private   String LogsTime;

    @ColumnInfo(name = "STORENO")
    private   String storeNo;
    @ColumnInfo(name = "ZONENAME")
    private  String ZONENAME;

    @ColumnInfo(name = "ZONETYPE")
    private  String ZONETYPE;


    @ColumnInfo(name = "Preqty")
    private  String Preqty;


    public int getSERIALLogs() {
        return SERIALLogs;
    }

    public void setSERIALLogs(int SERIALLogs) {
        this.SERIALLogs = SERIALLogs;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    public String getNewqty() {
        return Newqty;
    }

    public void setNewqty(String newqty) {
        Newqty = newqty;
    }

    public String getLogsDATE() {
        return LogsDATE;
    }

    public void setLogsDATE(String logsDATE) {
        LogsDATE = logsDATE;
    }

    public String getLogsTime() {
        return LogsTime;
    }

    public void setLogsTime(String logsTime) {
        LogsTime = logsTime;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getZONENAME() {
        return ZONENAME;
    }

    public void setZONENAME(String ZONENAME) {
        this.ZONENAME = ZONENAME;
    }

    public String getZONETYPE() {
        return ZONETYPE;
    }

    public void setZONETYPE(String ZONETYPE) {
        this.ZONETYPE = ZONETYPE;
    }

    public String getPreqty() {
        return Preqty;
    }

    public void setPreqty(String preqty) {
        Preqty = preqty;
    }
}
