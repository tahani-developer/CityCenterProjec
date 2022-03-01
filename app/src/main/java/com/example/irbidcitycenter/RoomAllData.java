package com.example.irbidcitycenter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.irbidcitycenter.Interfaces.AllPOsDao;
import com.example.irbidcitycenter.Interfaces.CompanyDao;
import com.example.irbidcitycenter.Interfaces.ItemDao;
import com.example.irbidcitycenter.Interfaces.RepReversDao;
import com.example.irbidcitycenter.Interfaces.ReplacementDao;
import com.example.irbidcitycenter.Interfaces.ReplashmentLogsDao;
import com.example.irbidcitycenter.Interfaces.ReplashmentReversLogsDao;
import com.example.irbidcitycenter.Interfaces.SettingDao;
import com.example.irbidcitycenter.Interfaces.ShipmentDao;
import com.example.irbidcitycenter.Interfaces.ShipmentLogsDao;
import com.example.irbidcitycenter.Interfaces.StocktakeDao;
import com.example.irbidcitycenter.Interfaces.StocktakelogsDao;
import com.example.irbidcitycenter.Interfaces.StoreDao;
import com.example.irbidcitycenter.Interfaces.UserPermissionsDao;
import com.example.irbidcitycenter.Interfaces.ZoneDao;

import com.example.irbidcitycenter.Interfaces.ZoneLogsDao;
import com.example.irbidcitycenter.Interfaces.ZoneRepLogsDao;
import com.example.irbidcitycenter.Interfaces.ZoneReplashmentDao;
import com.example.irbidcitycenter.Interfaces.ZonsDataDao;
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.AllPOs;
import com.example.irbidcitycenter.Models.CompanyInfo;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplashmentLogs;
import com.example.irbidcitycenter.Models.ReplashmentReversLogs;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ShipmentLogs;
import com.example.irbidcitycenter.Models.StocktakeLogs;
import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.UserPermissions;
import com.example.irbidcitycenter.Models.ZoneLogs;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneRepLogs;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;
import com.example.irbidcitycenter.Models.ZonsData;
import com.example.irbidcitycenter.Models.appSettings;


@Database(entities =  {ZoneModel.class, Shipment.class, ReplacementModel.class, appSettings.class, ZoneLogs.class, ShipmentLogs.class, ReplashmentLogs.class, StocktakeModel.class,StocktakeLogs.class, AllItems.class, Store.class, ZoneReplashmentModel.class, ZoneRepLogs.class, UserPermissions.class, ReplenishmentReverseModel.class, ReplashmentReversLogs.class, CompanyInfo.class, AllPOs.class, ZonsData.class}, version = 45,exportSchema = false)
public abstract class RoomAllData extends RoomDatabase  {
    private  static  RoomAllData database;
    public  static  String dataBaseName="DBRoomIrbidCenter";
    public abstract ZoneDao zoneDao();
    public abstract ShipmentDao shipmentDao();
    public abstract ReplacementDao replacementDao();
    public abstract SettingDao settingDao();
    public abstract ZoneLogsDao zoneLogsDao();
    public abstract ShipmentLogsDao shipmentLogsDao();
    public abstract ReplashmentLogsDao replashmentLogsDao();
    public abstract StocktakeDao  stocktakeDao();
    public abstract StocktakelogsDao stocktakelogsDao();
    public abstract ItemDao itemDao();
    public abstract StoreDao storeDao();
    public abstract ZoneReplashmentDao zoneReplashmentDao();
    public abstract ZoneRepLogsDao zoneRepLogsDao();
    public abstract UserPermissionsDao userPermissionsDao();
    public abstract RepReversDao repReversDao();
    public abstract CompanyDao companyDao();
    public abstract ZonsDataDao zonsDataDao();
    public abstract AllPOsDao allPOsDao();
    public abstract ReplashmentReversLogsDao replashmentReversLogsDao();
    static final Migration MIGRATION_41_42 = new Migration(41, 42) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

           database.execSQL("CREATE TABLE Company_TABLE (CoNo TEXT,CoYear TEXT,CoNameA TEXT) ");

        }
    };
    static final Migration MIGRATION_42_43 = new Migration(42, 43) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE ADD " + "logoimage" + " BLOB");


        }
    };
    static final Migration MIGRATION_42_44 = new Migration(42, 44) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SETTINGS_TABLE ADD " + "mainimage" + " BLOB");


        }
    };
    static final Migration MIGRATION_44_45 = new Migration(44, 45) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SHIPMENT_TABLE ADD COLUMN receivedBox TEXT");


        }
    };
    static final Migration MIGRATION_creatzone44_45 = new Migration(44, 45) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS ZonsDataTABLE " +
                    "(PRIMARY KEY (SERIAL)" +
                    ",ZONECODE TEXT, " +
                    "ZONENAME TEXT, " +
                    "ZONETYPE TEXT) ");

        }
    };
    static final Migration MIGRATION_creatPoData44_45 = new Migration(44, 45) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS AllPOsTABLE " +
                    "(PRIMARY KEY (SERIAL), " +
                    "PoNum TEXT) ");

        }
    };
    public static synchronized  RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class,dataBaseName)

                 // .addMigrations(MIGRATION_42_43,MIGRATION_42_44,MIGRATION_creatzone44_45,MIGRATION_creatPoData44_45,MIGRATION_44_45)
                    .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
                //   .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;
    }



}








