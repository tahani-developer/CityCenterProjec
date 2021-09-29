package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ZONEReplenishmentLOGSTABLE")
public class ZoneRepLogs  {
    @PrimaryKey(autoGenerate = true)
    public int SERIALZONE;

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


    @ColumnInfo(name = "Preqty")
    private  String Preqty;

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
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

    public String getPreqty() {
        return Preqty;
    }

    public void setPreqty(String preqty) {
        Preqty = preqty;
    }
}
