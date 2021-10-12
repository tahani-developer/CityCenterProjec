package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "UserPermissions_TABLE")
public class UserPermissions {
    @ColumnInfo(name = "UserNO")
    String UserNO;
    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    @ColumnInfo(name = "Setting_Per")
    String Setting_Per;
    @ColumnInfo(name = "SaveSetting_Per")
    String SaveSetting_Per;
    @ColumnInfo(name = "Import_Per")
    String Import_Per;
    @ColumnInfo(name = "Export_Per")
    String Export_Per;
    @ColumnInfo(name = "Exit_Per")
    String Exit_Per;



    @ColumnInfo(name = "SHIP_Save")
    String SHIP_Save;
    @ColumnInfo(name = "SHIP_LocalDelete")
    String SHIP_LocalDelete;
    @ColumnInfo(name = "SHIP_RemotelyDelete")
    String SHIP_RemotelyDelete;

    @ColumnInfo(name = "AddZone_Save")
    String AddZone_Save;
    @ColumnInfo(name = "AddZone_LocalDelete")
    String AddZone_LocalDelete;
    @ColumnInfo(name = "AddZone_RemotelyDelete")
    String AddZone_RemotelyDelete;

    @ColumnInfo(name = "Rep_Save")
    String Rep_Save;
    @ColumnInfo(name = "Rep_LocalDelete")
    String Rep_LocalDelete;
    @ColumnInfo(name = "Rep_RemotelyDelete")
    String Rep_RemotelyDelete;

    @ColumnInfo(name = "StockTake_Save")
    String StockTake_Save;
    @ColumnInfo(name = "StockTake_LocalDelete")
    String StockTake_LocalDelete;
    @ColumnInfo(name = "StockTake_RemotelyDelete")
    String StockTake_RemotelyDelete;
    @ColumnInfo(name = "StockTake_UpdateQty")
    String StockTake_UpdateQty;



    @ColumnInfo(name = "ZoneRep_Save")
    String ZoneRep_Save;
    @ColumnInfo(name = "ZoneRep_LocalDelete")
    String ZoneRep_LocalDelete;
    @ColumnInfo(name = "ZoneRep_RemotelyDelete")
    String ZoneRep_RemotelyDelete;
    @ColumnInfo(name = "ZoneRep_UpdateQty")
    String ZoneRep_UpdateQty;



    public String getUserNO() {
        return UserNO;
    }

    public void setUserNO(String userNO) {
        UserNO = userNO;
    }

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getSetting_Per() {
        return Setting_Per;
    }

    public void setSetting_Per(String setting_Per) {
        Setting_Per = setting_Per;
    }

    public String getSaveSetting_Per() {
        return SaveSetting_Per;
    }

    public void setSaveSetting_Per(String saveSetting_Per) {
        SaveSetting_Per = saveSetting_Per;
    }

    public String getImport_Per() {
        return Import_Per;
    }

    public void setImport_Per(String import_Per) {
        Import_Per = import_Per;
    }

    public String getExport_Per() {
        return Export_Per;
    }

    public void setExport_Per(String export_Per) {
        Export_Per = export_Per;
    }

    public String getExit_Per() {
        return Exit_Per;
    }

    public void setExit_Per(String exit_Per) {
        Exit_Per = exit_Per;
    }

    public String getSHIP_Save() {
        return SHIP_Save;
    }

    public void setSHIP_Save(String SHIP_Save) {
        this.SHIP_Save = SHIP_Save;
    }

    public String getSHIP_LocalDelete() {
        return SHIP_LocalDelete;
    }

    public void setSHIP_LocalDelete(String SHIP_LocalDelete) {
        this.SHIP_LocalDelete = SHIP_LocalDelete;
    }

    public String getSHIP_RemotelyDelete() {
        return SHIP_RemotelyDelete;
    }

    public void setSHIP_RemotelyDelete(String SHIP_RemotelyDelete) {
        this.SHIP_RemotelyDelete = SHIP_RemotelyDelete;
    }

    public String getAddZone_Save() {
        return AddZone_Save;
    }

    public void setAddZone_Save(String addZone_Save) {
        AddZone_Save = addZone_Save;
    }

    public String getAddZone_LocalDelete() {
        return AddZone_LocalDelete;
    }

    public void setAddZone_LocalDelete(String addZone_LocalDelete) {
        AddZone_LocalDelete = addZone_LocalDelete;
    }

    public String getAddZone_RemotelyDelete() {
        return AddZone_RemotelyDelete;
    }

    public void setAddZone_RemotelyDelete(String addZone_RemotelyDelete) {
        AddZone_RemotelyDelete = addZone_RemotelyDelete;
    }

    public String getRep_Save() {
        return Rep_Save;
    }

    public void setRep_Save(String rep_Save) {
        Rep_Save = rep_Save;
    }

    public String getRep_LocalDelete() {
        return Rep_LocalDelete;
    }

    public void setRep_LocalDelete(String rep_LocalDelete) {
        Rep_LocalDelete = rep_LocalDelete;
    }

    public String getRep_RemotelyDelete() {
        return Rep_RemotelyDelete;
    }

    public void setRep_RemotelyDelete(String rep_RemotelyDelete) {
        Rep_RemotelyDelete = rep_RemotelyDelete;
    }

    public String getStockTake_Save() {
        return StockTake_Save;
    }

    public void setStockTake_Save(String stockTake_Save) {
        StockTake_Save = stockTake_Save;
    }

    public String getStockTake_LocalDelete() {
        return StockTake_LocalDelete;
    }

    public void setStockTake_LocalDelete(String stockTake_LocalDelete) {
        StockTake_LocalDelete = stockTake_LocalDelete;
    }

    public String getStockTake_RemotelyDelete() {
        return StockTake_RemotelyDelete;
    }

    public void setStockTake_RemotelyDelete(String stockTake_RemotelyDelete) {
        StockTake_RemotelyDelete = stockTake_RemotelyDelete;
    }

    public String getZoneRep_Save() {
        return ZoneRep_Save;
    }

    public void setZoneRep_Save(String zoneRep_Save) {
        ZoneRep_Save = zoneRep_Save;
    }

    public String getZoneRep_LocalDelete() {
        return ZoneRep_LocalDelete;
    }

    public void setZoneRep_LocalDelete(String zoneRep_LocalDelete) {
        ZoneRep_LocalDelete = zoneRep_LocalDelete;
    }

    public String getZoneRep_RemotelyDelete() {
        return ZoneRep_RemotelyDelete;
    }

    public void setZoneRep_RemotelyDelete(String zoneRep_RemotelyDelete) {
        ZoneRep_RemotelyDelete = zoneRep_RemotelyDelete;
    }

    public String getStockTake_UpdateQty() {
        return StockTake_UpdateQty;
    }

    public void setStockTake_UpdateQty(String stockTake_UpdateQty) {
        StockTake_UpdateQty = stockTake_UpdateQty;
    }

    public String getZoneRep_UpdateQty() {
        return ZoneRep_UpdateQty;
    }

    public void setZoneRep_UpdateQty(String zoneRep_UpdateQty) {
        ZoneRep_UpdateQty = zoneRep_UpdateQty;
    }

}
