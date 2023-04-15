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
    @ColumnInfo(name = "CONO1")
    String  CONO1;
    @ColumnInfo(name = "CONO2")
    String  CONO2;
    @ColumnInfo(name = "CONO3")
    String  CONO3;
    @ColumnInfo(name = "CONO4")
    String  CONO4;
    @ColumnInfo(name = "CONO5")
    String  CONO5;
    @ColumnInfo(name = "CONO6")
    String  CONO6;
    @ColumnInfo(name = "CONO7")
    String  CONO7;
    @ColumnInfo(name = "CONO8")
    String  CONO8;
    @ColumnInfo(name = "CONO9")
    String  CONO9;
    @ColumnInfo(name = "CONO10")
    String  CONO10;


    @ColumnInfo(name = "UserActive")
    String UserActive;
    @ColumnInfo(name = "MasterUser")
    String MasterUser;
    @ColumnInfo(name = "SH_RepOpen")
    String SH_RepOpen;
    @ColumnInfo(name = "ST_RepOpen")
    String ST_RepOpen;


    @ColumnInfo(name = "RevRep_Open")
    String RevRep_Open;
    @ColumnInfo(name = "OVIEWCOST")
    String VIEWCost;
    @ColumnInfo(name = "RepRev_LocalDelete")
    String RepRev_LocalDelete;


    ///new store perimsssion
    @ColumnInfo(name = "COSTORE1",defaultValue = "")
    String COSTORE1;
    @ColumnInfo(name = "COSTORE2",defaultValue = "")
    String COSTORE2;
    @ColumnInfo(name = "COSTORE3",defaultValue = "")
    String COSTORE3;
    @ColumnInfo(name = "COSTORE4",defaultValue = "")
    String COSTORE4;
    @ColumnInfo(name = "COSTORE5",defaultValue = "")
    String COSTORE5;
    @ColumnInfo(name = "COSTORE6",defaultValue = "")
    String COSTORE6;
    @ColumnInfo(name = "COSTORE7",defaultValue = "")
    String COSTORE7;
    @ColumnInfo(name = "COSTORE8",defaultValue = "")
    String COSTORE8;
    @ColumnInfo(name = "COSTORE9",defaultValue = "")
    String COSTORE9;
    @ColumnInfo(name = "COSTORE10",defaultValue = "")
    String COSTORE10;

    public String getRevRep_Open() {
        return RevRep_Open;
    }

    public void setRevRep_Open(String revRep_Open) {
        RevRep_Open = revRep_Open;
    }

    public String getVIEWCost() {
        return VIEWCost;
    }

    public void setVIEWCost(String VIEWCost) {
        this.VIEWCost = VIEWCost;
    }

    public String getRepRev_LocalDelete() {
        return RepRev_LocalDelete;
    }

    public void setRepRev_LocalDelete(String repRev_LocalDelete) {
        RepRev_LocalDelete = repRev_LocalDelete;
    }



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

    public String getCONO1() {
        return CONO1;
    }

    public void setCONO1(String CONO1) {
        this.CONO1 = CONO1;
    }

    public String getCONO2() {
        return CONO2;
    }

    public void setCONO2(String CONO2) {
        this.CONO2 = CONO2;
    }

    public String getCONO3() {
        return CONO3;
    }

    public void setCONO3(String CONO3) {
        this.CONO3 = CONO3;
    }

    public String getCONO4() {
        return CONO4;
    }

    public void setCONO4(String CONO4) {
        this.CONO4 = CONO4;
    }

    public String getCONO5() {
        return CONO5;
    }

    public void setCONO5(String CONO5) {
        this.CONO5 = CONO5;
    }

    public String getCONO6() {
        return CONO6;
    }

    public void setCONO6(String CONO6) {
        this.CONO6 = CONO6;
    }

    public String getCONO7() {
        return CONO7;
    }

    public void setCONO7(String CONO7) {
        this.CONO7 = CONO7;
    }

    public String getCONO8() {
        return CONO8;
    }

    public void setCONO8(String CONO8) {
        this.CONO8 = CONO8;
    }

    public String getCONO9() {
        return CONO9;
    }

    public void setCONO9(String CONO9) {
        this.CONO9 = CONO9;
    }

    public String getCONO10() {
        return CONO10;
    }

    public void setCONO10(String CONO10) {
        this.CONO10 = CONO10;
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

    public String getCOSTORE1() {
        return this.COSTORE1;
    }

    public void setCOSTORE1(final String COSTORE1) {
        this.COSTORE1 = COSTORE1;
    }

    public String getCOSTORE2() {
        return this.COSTORE2;
    }

    public void setCOSTORE2(final String COSTORE2) {
        this.COSTORE2 = COSTORE2;
    }

    public String getCOSTORE3() {
        return this.COSTORE3;
    }

    public void setCOSTORE3(final String COSTORE3) {
        this.COSTORE3 = COSTORE3;
    }

    public String getCOSTORE4() {
        return this.COSTORE4;
    }

    public void setCOSTORE4(final String COSTORE4) {
        this.COSTORE4 = COSTORE4;
    }

    public String getCOSTORE5() {
        return this.COSTORE5;
    }

    public void setCOSTORE5(final String COSTORE5) {
        this.COSTORE5 = COSTORE5;
    }

    public String getCOSTORE6() {
        return this.COSTORE6;
    }

    public void setCOSTORE6(final String COSTORE6) {
        this.COSTORE6 = COSTORE6;
    }

    public String getCOSTORE7() {
        return this.COSTORE7;
    }

    public void setCOSTORE7(final String COSTORE7) {
        this.COSTORE7 = COSTORE7;
    }

    public String getCOSTORE8() {
        return this.COSTORE8;
    }

    public void setCOSTORE8(final String COSTORE8) {
        this.COSTORE8 = COSTORE8;
    }

    public String getCOSTORE9() {
        return this.COSTORE9;
    }

    public void setCOSTORE9(final String COSTORE9) {
        this.COSTORE9 = COSTORE9;
    }

    public String getCOSTORE10() {
        return this.COSTORE10;
    }

    public void setCOSTORE10(final String COSTORE10) {
        this.COSTORE10 = COSTORE10;
    }
}
