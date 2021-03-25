package com.example.irbidcitycenter.Models;

public  class ReplacementModel {

    String From;
    String To;
    String Zone;
    String Itemcode;
    String Qty;

    public ReplacementModel(String from, String to, String zone, String itemcode, String qty) {
        From = from;
        To = to;
        Zone = zone;
        Itemcode = itemcode;
        Qty = qty;
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
}

