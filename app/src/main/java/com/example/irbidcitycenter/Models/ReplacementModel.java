package com.example.irbidcitycenter.Models;

public  class ReplacementModel {

    String From;
    String To;
    String Zone;
    String Itemcode;
    String IsPosted;
    String ReplacementDate;
    String Qty;
    String Serial;

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

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
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

