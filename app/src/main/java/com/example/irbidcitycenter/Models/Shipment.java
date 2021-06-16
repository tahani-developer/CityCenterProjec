package com.example.irbidcitycenter.Models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Entity(tableName = "SHIPMENT_TABLE")
public  class Shipment {
    @PrimaryKey(autoGenerate = true)
     int SERIAL;
    @ColumnInfo(name = "PONO")
    String PoNo;
    @ColumnInfo(name = "BOXNO")
    String BoxNo;
    @ColumnInfo(name = "BARECODE")
    String Barcode;
    @ColumnInfo(name = "SHIPMENTDATE")
    String ShipmentDate;
    @ColumnInfo(name = "SHIPMENTTIME")
    String ShipmentTime;
    @ColumnInfo(name = "QTY")
    String Qty;
    @ColumnInfo(name = "ISPOSTED" ,defaultValue = "0")
    String IsPosted;

    @ColumnInfo(name = "ITEMNAME")
    String Itemname ;
    @ColumnInfo(name = "PoQTY")
    String Poqty;

    String Differ ;



    public String getDiffer() {
        return Differ;
    }

    public void setDiffer(String differ) {
        Differ = differ;
    }

    public String getPoqty() {
        return Poqty;
    }

    public void setPoqty(String poqty) {
        Poqty = poqty;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getIsPosted() {
        return IsPosted;
    }

    public void setIsPosted(String isPosted) {
        IsPosted = isPosted;
    }

    public String getShipmentDate() {
        return ShipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        ShipmentDate = shipmentDate;
    }

    public String getShipmentTime() {
        return ShipmentTime;
    }

    public void setShipmentTime(String shipmentTime) {
        ShipmentTime = shipmentTime;
    }

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public Shipment() {
    }

    public Shipment(String poNo, String boxNo, String barcode, String qty) {
        PoNo = poNo;
        BoxNo = boxNo;
        Barcode = barcode;
        Qty = qty;
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

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public  String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
    public JSONObject getJSONObjectDelphi() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("PONO", PoNo);
            obj.put("BOXNO", BoxNo);
            obj.put("ITEMCODE", Barcode);
            obj.put("SHIPMENTDATE", ShipmentDate);
            obj.put("SHIPMENTTIME", ShipmentTime);
            obj.put("QTY", Qty);
            obj.put("ISPOSTED", IsPosted);
            obj.put("ITEMNAME", Itemname);
            obj.put("POQTY", Poqty);




        } catch (JSONException e) {
            Log.e("Tag" , "JSONException");
        }
        return obj;
    }
}
