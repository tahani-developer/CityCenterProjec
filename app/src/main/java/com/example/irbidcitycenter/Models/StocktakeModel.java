package com.example.irbidcitycenter.Models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "STOCKTAKE_TABLE")
public class StocktakeModel {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    @ColumnInfo(name = "UserNO")
    String UserNO;
    @ColumnInfo(name = "STORE")
    String Store;
    @ColumnInfo(name = "ZONECODE")
    String Zone;
    @ColumnInfo(name = "ITEMOCODE")
    String ItemOcode;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    @ColumnInfo(name = "DATE")
    String Date;
    @ColumnInfo(name = "TIME")
    String Time;
    @ColumnInfo(name = "QTY")
    String Qty;
    @ColumnInfo(name = "ITEMNAME")
    String ItemName;
    @ColumnInfo(name = "DEVICEID")
    String deviceId;
    @ColumnInfo(name = "StoreName" ,defaultValue = "")
    String StoreName;

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("STORE", Store);
            obj.put("USERNO", UserNO);
            obj.put("ZONECODE", Zone);
            obj.put("ITEMOCODE", ItemOcode);
            obj.put("DATE", Date);
            obj.put("TIME", Time);
            obj.put("QTY",Qty);
            obj.put("ITEMNAME",ItemName);
            obj.put("DEVICEID",deviceId);


        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

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

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getItemOcode() {
        return ItemOcode;
    }

    public void setItemOcode(String itemOcode) {
        ItemOcode = itemOcode;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
