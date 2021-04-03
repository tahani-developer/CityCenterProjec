package com.example.irbidcitycenter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.irbidcitycenter.Interfaces.ZoneDao;
import com.example.irbidcitycenter.Models.ZoneModel;

@Database(entities =  {ZoneModel.class}, version = 2,exportSchema = false)
public abstract class RoomAllData extends RoomDatabase  {
    private  static  RoomAllData database;
    public  static  String dataBaseName="DBRoomIrbidCenter";
    public abstract ZoneDao zoneDao();

    public static synchronized  RoomAllData getInstanceDataBase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), RoomAllData.class,dataBaseName).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;
    }

}

