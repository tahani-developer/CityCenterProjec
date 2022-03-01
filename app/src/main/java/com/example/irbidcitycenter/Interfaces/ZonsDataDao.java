package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZonsData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ZonsDataDao {
    @Insert
    public long[] insertAll(List<ZonsData> zones);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ArrayList<ZonsData> zones);

    @Delete
    public void delete(ZonsData item);
    @Query("SELECT * FROM ZonsDataTABLE ")
    public List<ZonsData>  getAll();

    @Query("DELETE FROM ZonsDataTABLE")
    public void deleteAll();

}
