package com.example.irbidcitycenter.Models;

public  class Shipment {
    String Serial;
    String PoNo;
    String BoxNo;
    String Barcode;
    String ShipmentDate;
    String ShipmentTime;
    int Qty;
    String IsPosted;

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

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public Shipment() {
    }

    public Shipment(String poNo, String boxNo, String barcode, int qty) {
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

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
