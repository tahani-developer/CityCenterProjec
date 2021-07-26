package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ReplashmentLogs;
import com.example.irbidcitycenter.Models.ShipmentLogs;

@Dao
public
interface ReplashmentLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ReplashmentLogs replashmentLogs);


    @Delete
    public void delete(ReplashmentLogs replashmentLogs);


    @Update
    public  void update(ReplashmentLogs replashmentLogs);

    @Query("DELETE FROM ReplashmentLOGSTABLE")
    void deleteAll();
}
