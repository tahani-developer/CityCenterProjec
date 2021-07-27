package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ShipmentLogs;
import com.example.irbidcitycenter.Models.ZoneLogs;

@Dao
public interface ShipmentLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ShipmentLogs shipmentLogs);


    @Delete
    public void delete(ShipmentLogs shipmentLogs);


    @Update
    public  void update(ShipmentLogs shipmentLogs);

    @Query("DELETE FROM SHIPMENTLOGSTABLE")
    void deleteAll();
}
