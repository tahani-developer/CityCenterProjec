package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.NewZonsData;
import com.example.irbidcitycenter.Models.ZonsData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewZonsDataDao {
    @Insert
    public long[] insertAll(List<NewZonsData> zones);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ArrayList<NewZonsData> zones);

    @Delete
    public void delete(NewZonsData item);
    @Query("SELECT * FROM NewZonsDataTABLE ")
    public List<NewZonsData>  getAll();

    @Query("DELETE FROM NewZonsDataTABLE")
    public void deleteAll();

    @Query("SELECT * FROM NewZonsDataTABLE where ITEMCODE= :itemno")
    public NewZonsData  getItemInfo(String itemno);

    @Query("SELECT * FROM NewZonsDataTABLE where ZONENO= :zone and ITEMCODE= :itemno")
    public NewZonsData  getqty(String zone,String itemno);

}
