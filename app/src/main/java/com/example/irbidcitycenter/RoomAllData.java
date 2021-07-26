package com.example.irbidcitycenter;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.irbidcitycenter.Interfaces.ReplacementDao;
import com.example.irbidcitycenter.Interfaces.SettingDao;
import com.example.irbidcitycenter.Interfaces.ShipmentDao;
import com.example.irbidcitycenter.Interfaces.ZoneDao;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.appSettings;

@Database(entities =  {ZoneModel.class, Shipment.class, ReplacementModel.class, appSettings.class}, version = 21,exportSchema = false)
public abstract class RoomAllData extends RoomDatabase  {
    private  static  RoomAllData database;
    public  static  String dataBaseName="DBRoomIrbidCenter";
    public abstract ZoneDao zoneDao();
    public abstract ShipmentDao shipmentDao();
    public abstract ReplacementDao replacementDao();
    public abstract SettingDao settingDao();
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
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                     .fallbackToDestructiveMigration()
                    .build();
                   // .allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;
    }



}








