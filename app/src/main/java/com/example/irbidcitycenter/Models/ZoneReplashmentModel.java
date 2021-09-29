package com.example.irbidcitycenter.Models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "ZONEREPLASHMENT_TABLE")
public class ZoneReplashmentModel {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;
    @ColumnInfo(name = "UserNO")
    String UserNO;
    @ColumnInfo(name = "FromZone")
    String FromZone;
    @ColumnInfo(name = "ToZone")
    String ToZone;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "Time")
    String Time;
    @ColumnInfo(name = "ItemCode")
    String itemcode;
    @ColumnInfo(name = "Qty")
    String Qty;
    @ColumnInfo(name = "RecQty")
    String RecQty;
    @ColumnInfo(name = "DEVICEID")
    String deviceId;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    public ZoneReplashmentModel() {
    }

    public int getSERIAL() {
        return SERIAL;
    }

    public String getRecQty() {
        return RecQty;
    }

    public void setRecQty(String recQty) {
        RecQty = recQty;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
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

    public String getFromZone() {
        return FromZone;
    }

    public void setFromZone(String fromZone) {
        FromZone = fromZone;
    }

    public String getToZone() {
        return ToZone;
    }

    public void setToZone(String toZone) {
        ToZone = toZone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }


    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("FROMZONENO",FromZone );
            obj.put("TOZONENO", ToZone);
            obj.put("ITEMCODE",itemcode );
            obj.put("QTY",Qty );
            obj.put("TRDATE",date );
            obj.put("TRTIME",Time );
            obj.put("DEVICEID", deviceId);

        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
