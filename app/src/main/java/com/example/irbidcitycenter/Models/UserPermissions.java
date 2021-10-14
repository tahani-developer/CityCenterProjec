package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "UserPermissions_TABLE")
public class UserPermissions {
    @ColumnInfo(name = "UserNO")
    String UserNO;
    @ColumnInfo(name = "UserName")
    String UserName;
    @ColumnInfo(name = "UserPassword")
    String UserPassword;

    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    @ColumnInfo(name = "Setting_Per")
    String Setting_Per;

    @ColumnInfo(name = "Import_Per")
    String Import_Per;
    @ColumnInfo(name = "Export_Per")
    String Export_Per;




    @ColumnInfo(name = "SHIP_Save")
    String SHIP_Save;
    @ColumnInfo(name = "SHIP_Open")
    String SHIP_Open;
    @ColumnInfo(name = "SHIP_LocalDelete")
    String SHIP_LocalDelete;


    @ColumnInfo(name = "AddZone_Save")
    String AddZone_Save;
    @ColumnInfo(name = "AddZone_Open")
    String AddZone_Open;
    @ColumnInfo(name = "AddZone_LocalDelete")
    String AddZone_LocalDelete;


    @ColumnInfo(name = "Rep_Save")
    String Rep_Save;
    @ColumnInfo(name = "Repe_Open")
    String Rep_Open;
    @ColumnInfo(name = "Rep_LocalDelete")
    String Rep_LocalDelete;


    @ColumnInfo(name = "StockTake_Save")
    String StockTake_Save;
    @ColumnInfo(name = "StockTake_Open")
    String StockTake_Open;
    @ColumnInfo(name = "StockTake_LocalDelete")
    String StockTake_LocalDelete;

    @ColumnInfo(name = "StockTake_UpdateQty")
    String StockTake_UpdateQty;



    @ColumnInfo(name = "ZoneRep_Save")
    String ZoneRep_Save;
    @ColumnInfo(name = "ZoneRep_Open")
    String ZoneRep_Open;
    @ColumnInfo(name = "ZoneRep_LocalDelete")
    String ZoneRep_LocalDelete;

    @ColumnInfo(name = "ZoneRep_UpdateQty")
    String ZoneRep_UpdateQty;
    @ColumnInfo(name = "CONO")
    String  CONO;

    @ColumnInfo(name = "UserActive")
    String UserActive;
    @ColumnInfo(name = "MasterUser")
    String MasterUser;
    @ColumnInfo(name = "SH_RepOpen")
    String SH_RepOpen;
    @ColumnInfo(name = "ST_RepOpen")
    String ST_RepOpen;
    public String getSHIP_Open() {
        return SHIP_Open;
    }

    public String getUserActive() {
        return UserActive;
    }

    public void setUserActive(String userActive) {
        UserActive = userActive;
    }

    public String getMasterUser() {
        return MasterUser;
    }

    public void setMasterUser(String masterUser) {
        MasterUser = masterUser;
    }

    public String getSH_RepOpen() {
        return SH_RepOpen;
    }

    public void setSH_RepOpen(String SH_RepOpen) {
        this.SH_RepOpen = SH_RepOpen;
    }

    public String getST_RepOpen() {
        return ST_RepOpen;
    }

    public void setST_RepOpen(String ST_RepOpen) {
        this.ST_RepOpen = ST_RepOpen;
    }

    public void setSHIP_Open(String SHIP_Open) {
        this.SHIP_Open = SHIP_Open;
    }

    public String getAddZone_Open() {
        return AddZone_Open;
    }

    public void setAddZone_Open(String addZone_Open) {
        AddZone_Open = addZone_Open;
    }

    public String getRep_Open() {
        return Rep_Open;
    }

    public void setRep_Open(String rep_Open) {
        Rep_Open = rep_Open;
    }

    public String getStockTake_Open() {
        return StockTake_Open;
    }

    public void setStockTake_Open(String stockTake_Open) {
        StockTake_Open = stockTake_Open;
    }

    public String getZoneRep_Open() {
        return ZoneRep_Open;
    }

    public void setZoneRep_Open(String zoneRep_Open) {
        ZoneRep_Open = zoneRep_Open;
    }

    public String getUserName() {
        return UserName;
    }

    public String getCONO() {
        return CONO;
    }

    public void setCONO(String CONO) {
        this.CONO = CONO;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

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
