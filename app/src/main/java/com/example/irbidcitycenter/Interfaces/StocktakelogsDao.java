package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ReplashmentLogs;
import com.example.irbidcitycenter.Models.StocktakeLogs;

@Dao
public interface StocktakelogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(StocktakeLogs stocktakeLogs);


    @Delete
    public void delete(StocktakeLogs stocktakeLogs);


    @Update
    public  void update(StocktakeLogs stocktakeLogs);

    @Query("DELETE FROM STOCKTAKELOGSTABLE")
    void deleteAll();
}
