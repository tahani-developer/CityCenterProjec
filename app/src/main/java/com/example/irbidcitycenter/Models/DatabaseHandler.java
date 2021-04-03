package com.example.irbidcitycenter.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.irbidcitycenter.Replacement;

public class DatabaseHandler extends SQLiteOpenHelper {
    static SQLiteDatabase db;
    public static final String dbname="IrbidCityCenter.db";
    public static final int version=11;
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




        String CREATE_TABLE_SETTINGS2 = "CREATE TABLE if not EXISTS SHIPMENT_TABLE"  + "("
                + "SERIAL" + " int"
                + ",PONO" + " TEXT,"
                + "BOXNO" + " TEXT,"
                +"BARECODE" + " TEXT,"
                +"QTY" +" TEXT,"
                +"SHIPMENTDATE" + " TEXT,"
                +"SHIPMENTTIME" + " TEXT,"
                +"ISPOSTED" + " TEXT DEFAULT '0'"+
                ")";
        db.execSQL(CREATE_TABLE_SETTINGS2);

        String CREATE_TABLE_SETTINGS3 = "CREATE TABLE if not EXISTS REPLACEMENT_TABLE"  + "("
                + "SERIAL" + " int,"
                + "FROMSTORE" + " TEXT,"
                + "TOSTORE" + " TEXT,"
                +"ZONE" + " TEXT,"
                +"ITEMCODE" +" TEXT,"
                +"QTY" + " TEXT,"
                +"REPLACEMENTDATE" + " TEXT,"
                +"ISPOSTED" + " TEXT DEFAULT '0'"
                +")";
        db.execSQL(CREATE_TABLE_SETTINGS3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     String CREATE_TABLE_SETTINGS = "CREATE TABLE if not EXISTS " + SETTINGS_TABLE + "("
                + COMPANY_Num + " TEXT,"
                + IP_ADDRESS + " TEXT,"
                + Years + " TEXT,"
                +updateqty + " TEXT,"
                +USER_Num +" TEXT"+")";
        db.execSQL(CREATE_TABLE_SETTINGS);

        String CREATE_TABLE_SETTINGS2 = "CREATE TABLE if not EXISTS SHIPMENT_TABLE"  + "("
                + "SERIAL" + " int"
                + ",PONO" + " TEXT,"
                + "BOXNO" + " TEXT,"
                +"BARECODE" + " TEXT,"
                +"QTY" +" TEXT,"
                +"SHIPMENTDATE" + " TEXT,"
                +"SHIPMENTTIME" + " TEXT,"
                +"ISPOSTED" + " TEXT DEFAULT '0'"+
        ")";
        db.execSQL(CREATE_TABLE_SETTINGS2);

        String CREATE_TABLE_SETTINGS3 = "CREATE TABLE if not EXISTS REPLACEMENT_TABLE"  + "("
                + "SERIAL" + " int,"
                + "FROMSTORE" + " TEXT,"
                + "TOSTORE" + " TEXT,"
                +"ZONE" + " TEXT,"
                +"ITEMCODE" +" TEXT,"
                +"QTY" + " TEXT,"
                +"REPLACEMENTDATE" + " TEXT,"
                +"ISPOSTED" + " TEXT DEFAULT '0'"
                +")";


     //   db.execSQL("ALTER TABLE REPLACEMENT_TABLE ADD COLUMN SERIAL int");
   //     db.execSQL("ALTER TABLE SHIPMENT_TABLE ADD COLUMN SERIAL int");

       // db.execSQL("ALTER TABLE REPLACEMENT_TABLE ADD COLUMN PRIMARY KEY (SERIAL)");
        ;

    //    db.execSQL(CREATE_TABLE_SETTINGS3);

    //    db.execSQL("ALTER TABLE REPLACEMENT_TABLE ADD  AUTO_INCREMENT SERIAL PRIMARY KEY");
     //   db.execSQL("ALTER TABLE SHIPMENT_TABLE ADD  AUTO_INCREMENT SERIAL PRIMARY KEY");

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

    public void AddNewShipment(Shipment shipment){
     db = this.getWritableDatabase();
        String date =String.valueOf(java.time.LocalDate.now());
        String time=String.valueOf(java.time.LocalTime.now());
        ContentValues contentValues = new ContentValues();
      //  contentValues.put("SERIAL", shipment.getSerial());
        contentValues.put("PONO",shipment.getPoNo());
        contentValues.put("BOXNO",shipment.getBoxNo());
        contentValues.put("BARECODE",shipment.getBarcode());
        contentValues.put("QTY",shipment.getQty());
        contentValues.put("SHIPMENTDATE",date);
        contentValues.put("SHIPMENTTIME", time);
        db.insert("SHIPMENT_TABLE", null, contentValues);
        db.close();
    }


    public void addReplacement(ReplacementModel replacement){
        String date =String.valueOf(java.time.LocalDate.now());

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put("SERIAL", replacement.getSerial());
        contentValues.put("FROMSTORE", replacement.getFrom());
        contentValues.put("TOSTORE", replacement.getTo());
        contentValues.put("ITEMCODE", replacement.getItemcode());
        contentValues.put("QTY", replacement.getQty());
        contentValues.put("REPLACEMENTDATE", date);
      //  contentValues.put("ISPOSTED", replacement.getIsPosted());
        db.insert("REPLACEMENT_TABLE", null, contentValues);
        db.close();
    }
    public void DeleteShipment(String Serial){
        db = this.getWritableDatabase();
        db.delete("SHIPMENT_TABLE", "SERIAL", new String[]{Serial});
        db.close();
    }
    public void DeleteReplacement(String Serial ){
        db = this.getWritableDatabase();
        db.delete("REPLACEMENT_TABLE", "SERIAL", new String[]{Serial});
        db.close();
    }
    public void UpdateShipment(String Serial, String Newqty){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("QTY", Newqty);
     db.update("",values,"where SERIAL =", new String[]{Serial});
    }
    public void DeleteAllShipment(){
         db=getReadableDatabase();
         db.delete("SHIPMENT_TABLE",null,null) ;
    }
    public void DeleteAllReplacement(){
        db=getReadableDatabase();
        db.delete("REPLACEMENT_TABLE",null,null) ;
    }
}
