package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ZONETABLE")
public class ZoneModel {
    @PrimaryKey(autoGenerate = true)
    public int SERIALZONE;

    @ColumnInfo(name = "ZONECODE")
    private   String zoneCode;

    @ColumnInfo(name = "ITEMCODE")
    private   String itemCode;

    @ColumnInfo(name = "QTYZONE")
    private   String qty;

    @ColumnInfo(name = "ISPOSTED")
    private   String isPostd;

    @ColumnInfo(name = "ZONEDATE")
    private   String zoneDate;

    @ColumnInfo(name = "ZONETIME")
    private   String zoneTime;

    @ColumnInfo(name = "STORENO")
    private   String storeNo;
    @ColumnInfo(name = "ZONENAME")
    private  String ZONENAME;

    @ColumnInfo(name = "ZONETYPE")
    private  String ZONETYPE;

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

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
    }

    public String getIsPostd() {
        return isPostd;
    }

    public void setIsPostd(String isPostd) {
        this.isPostd = isPostd;
    }

    public String getZoneDate() {
        return zoneDate;
    }

    public void setZoneDate(String zoneDate) {
        this.zoneDate = zoneDate;
    }

    public String getZoneTime() {
        return zoneTime;
    }

    public void setZoneTime(String zoneTime) {
        this.zoneTime = zoneTime;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public ZoneModel() {
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
