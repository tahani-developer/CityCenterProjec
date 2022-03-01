package com.example.irbidcitycenter.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Company_TABLE")
public class CompanyInfo {

    @PrimaryKey(autoGenerate = true)
    public    int SERIAL;

    @ColumnInfo(name = "CoNo")
    private  String CoNo;
    @ColumnInfo(name = "CoYear")
    private  String CoYear;
    @ColumnInfo(name = "CoNameA")
    private  String CoNameA;

    public CompanyInfo() {
    }

    public int getSERIAL() {
        return this.SERIAL;
    }

    public void setSERIAL(final int SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getCoNo() {
        return CoNo;
    }

    public void setCoNo(String coNo) {
        CoNo = coNo;
    }

    public String getCoYear() {
        return CoYear;
    }

    public void setCoYear(String coYear) {
        CoYear = coYear;
    }

    public String getCoNameA() {
        return CoNameA;
    }

    public void setCoNameA(String coNameA) {
        CoNameA = coNameA;
    }
}
