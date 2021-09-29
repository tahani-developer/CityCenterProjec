package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ZoneLogs;
import com.example.irbidcitycenter.Models.ZoneRepLogs;
@Dao
public interface ZoneRepLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ZoneRepLogs zoneRepLogs);


    @Delete
    public void delete(ZoneRepLogs zoneRepLogs);


    @Update
    public  void update(ZoneRepLogs zoneRepLogs);

    @Query("DELETE FROM ZONEReplenishmentLOGSTABLE")
    void deleteAll();
}
