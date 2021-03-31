package com.example.irbidcitycenter.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    static SQLiteDatabase db;
    public static final String dbname="IrbidCityCenter.db";
    public static final int version=5;
    String SETTINGS_TABLE="SETTINGS_TABLE";
    String COMPANY_Num="COMPANY_Num";
    String IP_ADDRESS="IP_ADDRESS";
    String Years="Years";
    String updateqty ="updateqty";
    String USER_Num="USER_Num";




    public DatabaseHandler(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SETTINGS = "CREATE TABLE " + SETTINGS_TABLE + "("
                + COMPANY_Num + " TEXT,"
                + IP_ADDRESS + " TEXT,"
                + Years + " TEXT,"
                +updateqty + " TEXT,"
        +USER_Num +" TEXT"+")";
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String CREATE_TABLE_SETTINGS = "CREATE TABLE if not EXISTS  " + SETTINGS_TABLE + "("
                + COMPANY_Num + " TEXT,"
                + IP_ADDRESS + " TEXT,"
                + Years + " TEXT,"
                +updateqty + " TEXT,"
                +USER_Num +" TEXT"+")";
        db.execSQL(CREATE_TABLE_SETTINGS);
    }
    public void addSettings(appSettings settings) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COMPANY_Num, settings.getCompanyNum());
        contentValues.put(IP_ADDRESS, settings.getIP());
        contentValues.put(Years, settings.getYears());
        contentValues.put(USER_Num, settings.getUserNumber());
        contentValues.put(updateqty, settings.getUpdateQTY());
        db.insert(SETTINGS_TABLE, null, contentValues);
        db.close();
    }
    public appSettings  getSettings(){
        appSettings settings = new appSettings();
        String selectQuery = "SELECT * FROM " + SETTINGS_TABLE;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                settings.setIP(cursor.getString(0));
                settings.setCompanyNum(cursor.getString(1));
                settings.setYears(cursor.getString(2));
                settings.setUpdateQTY(cursor.getString(3));
                settings.setUserNumber(cursor.getString(4));

            } while (cursor.moveToNext());
        }
        return settings;
    }
    public void deleteSettings() {
        db = this.getWritableDatabase();
        db.delete(SETTINGS_TABLE, null, null);
        db.close();
    }
}
