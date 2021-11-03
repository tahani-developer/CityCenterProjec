package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEM_TABLE")
public  class AllItems {
    @PrimaryKey(autoGenerate = true)
    int SERIAL;

    public int getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(int SERIAL) {
        this.SERIAL = SERIAL;
    }

    @ColumnInfo(name = "ITEMNAMEE")
    String ItemNameE;
    @ColumnInfo(name = "ITEMOCODE")
    String ItemOcode;
    @ColumnInfo(name = "ItemNCode")
    String ItemNCode;

    @ColumnInfo(name = "ItemNameA")
    String ItemNameA;
    @ColumnInfo(name = "LLCPrice")
    String LLCPrice;
    @ColumnInfo(name = "LFCPrice")
    String LFCPrice;
    @ColumnInfo(name = "SalePrice")
    String SalePrice;

    @ColumnInfo(name = "OrdLvl")
    String OrdLvl;
    @ColumnInfo(name = "AVG_Cost")
    String AVG_Cost;
    @ColumnInfo(name = "Fifo")
    String Fifo;
    @ColumnInfo(name = "Lifo")
    String Lifo;
    @ColumnInfo(name = "ItemG")
    String ItemG;

    @ColumnInfo(name = "ItemK")
    String ItemK;
    @ColumnInfo(name = "ItemM")
    String ItemM;
    @ColumnInfo(name = "ItemU")
    String ItemU;
    @ColumnInfo(name = "IStatus")
    String IStatus;
    @ColumnInfo(name = "ItemL")
    String ItemL;


    @ColumnInfo(name = "F_D")
    String   F_D;
    @ColumnInfo(name = "ITEMGS")
    String    ITEMGS;


    public String getItemNameE() {
        return ItemNameE;
    }

    public void setItemNameE(String itemNameE) {
        ItemNameE = itemNameE;
    }

    public String getItemNCode() {
        return ItemNCode;
    }

    public void setItemNCode(String itemNCode) {
        ItemNCode = itemNCode;
    }

    public String getItemNameA() {
        return ItemNameA;
    }

    public void setItemNameA(String itemNameA) {
        ItemNameA = itemNameA;
    }

    public String getLLCPrice() {
        return LLCPrice;
    }

    public void setLLCPrice(String LLCPrice) {
        this.LLCPrice = LLCPrice;
    }

    public String getLFCPrice() {
        return LFCPrice;
    }

    public void setLFCPrice(String LFCPrice) {
        this.LFCPrice = LFCPrice;
    }

    public String getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(String salePrice) {
        SalePrice = salePrice;
    }

    public String getOrdLvl() {
        return OrdLvl;
    }

    public void setOrdLvl(String ordLvl) {
        OrdLvl = ordLvl;
    }

    public String getAVG_Cost() {
        return AVG_Cost;
    }

    public void setAVG_Cost(String AVG_Cost) {
        this.AVG_Cost = AVG_Cost;
    }

    public String getFifo() {
        return Fifo;
    }

    public void setFifo(String fifo) {
        Fifo = fifo;
    }

    public String getLifo() {
        return Lifo;
    }

    public void setLifo(String lifo) {
        Lifo = lifo;
    }

    public String getItemG() {
        return ItemG;
    }

    public void setItemG(String itemG) {
        ItemG = itemG;
    }

    public String getItemK() {
        return ItemK;
    }

    public void setItemK(String IItemK) {
        this.ItemK = IItemK;
    }

    public String getItemM() {
        return ItemM;
    }

    public void setItemM(String itemM) {
        ItemM = itemM;
    }

    public String getItemU() {
        return ItemU;
    }

    public void setItemU(String itemU) {
        ItemU = itemU;
    }

    public String getIStatus() {
        return IStatus;
    }

    public void setIStatus(String IStatus) {
        this.IStatus = IStatus;
    }

    public String getItemL() {
        return ItemL;
    }

    public void setItemL(String itemL) {
        ItemL = itemL;
    }

    public String getF_D() {
        return F_D;
    }

    public void setF_D(String f_D) {
        F_D = f_D;
    }

    public String getITEMGS() {
        return ITEMGS;
    }

    public void setITEMGS(String ITEMGS) {
        this.ITEMGS = ITEMGS;
    }

    public String getItemOcode() {
        return ItemOcode;
    }

    public void setItemOcode(String itemOcode) {
        ItemOcode = itemOcode;
    }
}
