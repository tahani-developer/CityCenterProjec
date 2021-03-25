package com.example.irbidcitycenter.Models;

public  class Shipment {

    String PoNo;
    String BoxNo;
    String Barcode;
    int Qty;

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
