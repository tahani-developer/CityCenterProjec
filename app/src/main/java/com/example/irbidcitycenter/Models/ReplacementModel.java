package com.example.irbidcitycenter.Models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "REPLACEMENT_TABLE")
public  class ReplacementModel {
    @ColumnInfo(name = "FROMSTORE")
    String From;
    @ColumnInfo(name = "TOSTORE")
    String To;
    @ColumnInfo(name = "ZONECODE")
    String Zone;
    @ColumnInfo(name = "ITEMCODE")
    String Itemcode;
    @ColumnInfo(name = "ISPOSTED")
    String IsPosted;
    @ColumnInfo(name = "REPLACEMENTDATE")
    String ReplacementDate;
    @ColumnInfo(name = "QTY")
    String Qty;
    public String getRecQty() {
        return recQty;
    }

    public void setRecQty(String recQty) {
        this.recQty = recQty;
    }

    @ColumnInfo(name = "RECQTY")
    private   String recQty;
    @PrimaryKey(autoGenerate = true)
    int SERIALZONE;

    public ReplacementModel(String from, String to, String zone, String itemcode, String qty) {
        From = from;
        To = to;
        Zone = zone;
        Itemcode = itemcode;
        Qty = qty;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
    }

    public String getReplacementDate() {
        return ReplacementDate;
    }

    public void setReplacementDate(String replacementDate) {
        ReplacementDate = replacementDate;
    }

    public int getSERIALZONE() {
        return SERIALZONE;
    }

    public void setSERIALZONE(int SERIALZONE) {
        this.SERIALZONE = SERIALZONE;
    }

    public ReplacementModel() {
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getItemcode() {
        return Itemcode;
    }

    public void setItemcode(String itemcode) {
        Itemcode = itemcode;
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
            obj.put("FROMSTR", From);
            obj.put("TOSTR", To);
            obj.put("ZONE", Zone);
            obj.put("ITEMCODE", Itemcode);
            obj.put("QTY", Qty);




        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}

