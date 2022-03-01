package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.AllPOs;
import com.example.irbidcitycenter.Models.ZonsData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AllPOsDao {
    @Insert
    public long[] insertAll(List<AllPOs> allPOs);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(AllPOs allPOs);

    @Delete
    public void delete(AllPOs allPOs);

    @Query("SELECT PoNum FROM AllPOsTABLE")
    public List<String> getAll();
    @Query("DELETE FROM AllPOsTABLE")
    public void deleteAll();
}
