package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ZoneLogs;

@Dao
public interface ZoneLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ZoneLogs logsZone);


    @Delete
    public void delete(ZoneLogs logsZone);


    @Update
    public  void update(ZoneLogs logsZone);

    @Query("DELETE FROM ZONELOGSTABLE")
    void deleteAll();
}
