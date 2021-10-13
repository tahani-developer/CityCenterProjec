package com.example.irbidcitycenter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.irbidcitycenter.Interfaces.ItemDao;
import com.example.irbidcitycenter.Interfaces.ReplacementDao;
import com.example.irbidcitycenter.Interfaces.ReplashmentLogsDao;
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
import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplashmentLogs;
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
import com.example.irbidcitycenter.Models.appSettings;


@Database(entities =  {ZoneModel.class, Shipment.class, ReplacementModel.class, appSettings.class, ZoneLogs.class, ShipmentLogs.class, ReplashmentLogs.class, StocktakeModel.class,StocktakeLogs.class, AllItems.class, Store.class, ZoneReplashmentModel.class, ZoneRepLogs.class, UserPermissions.class}, version = 34,exportSchema = false)
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
    static final Migration MIGRATION_1_2 = new Migration(19, 21) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
//                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };

    public static synchronized  RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomAllData.class,dataBaseName)
            //        .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                    .build();
                   // .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;
    }



}








