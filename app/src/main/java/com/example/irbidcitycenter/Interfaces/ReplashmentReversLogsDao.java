package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ReplashmentLogs;
import com.example.irbidcitycenter.Models.ReplashmentReversLogs;

@Dao
public interface ReplashmentReversLogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ReplashmentReversLogs replashmentLogs);


    @Delete
    public void delete(ReplashmentReversLogs replashmentLogs);


    @Update
    public  void update(ReplashmentReversLogs replashmentLogs);

    @Query("DELETE FROM ReplashmentReversLogsTABLE")
    void deleteAll();
}
