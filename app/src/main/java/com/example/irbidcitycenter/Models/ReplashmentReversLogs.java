package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "ReplashmentReversLogsTABLE")
public class ReplashmentReversLogs {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;

    @ColumnInfo(name = "UserNO")
    String UserNO;

    @ColumnInfo(name = "Store")
    private   String Store;


    @ColumnInfo(name = "ITEMCODE")
    String ItemCode;

    @ColumnInfo(name = "PREQTY")
    String preQty;

    @ColumnInfo(name = "NEWQTY")
    String NewQty;

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


    public String getStore() {
        return Store;
    }

    public void setStore(String store) {
        Store = store;
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
