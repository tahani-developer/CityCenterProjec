package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SHIPMENTLOGSTABLE")
public class ShipmentLogs  {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;

    @ColumnInfo(name = "UserNO")
    String UserNO;
    @ColumnInfo(name = "PONO")
    String PoNo;
    @ColumnInfo(name = "BOXNO")
    String BoxNo;
    @ColumnInfo(name = "ITEMCODE")
    String ItemCode;
    @ColumnInfo(name = "PREQTY")
    String preQty;
    @ColumnInfo(name = "NEWQTY")
    String NewQty;
    @ColumnInfo(name = "DIFFER")
    String Differ ;
    @ColumnInfo(name = "LogsDATE")
    private   String LogsDATE;

    @ColumnInfo(name = "LogsTIME")
    private   String LogsTime;

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    public String getPoNo() {
        return PoNo;
    }

    public void setPoNo(String poNo) {
        PoNo = poNo;
    }

    public String getBoxNo() {
        return BoxNo;
    }

    public void setBoxNo(String boxNo) {
        BoxNo = boxNo;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getPreQty() {
        return preQty;
    }

    public void setPreQty(String preQty) {
        this.preQty = preQty;
    }

    public String getNewQty() {
        return NewQty;
    }

    public void setNewQty(String newQty) {
        NewQty = newQty;
    }

    public String getDiffer() {
        return Differ;
    }

    public void setDiffer(String differ) {
        Differ = differ;
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
}
