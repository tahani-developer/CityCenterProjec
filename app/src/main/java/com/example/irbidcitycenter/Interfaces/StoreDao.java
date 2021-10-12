package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.Store;
import com.example.irbidcitycenter.Models.ZoneModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StoreDao {

    @Insert
    public long[] insertAll(List<Store> stores);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Store store);

    @Delete
    public void delete(Store store);


    @Update
    public  void update(Store store);
    @Query("SELECT * FROM STORE_TABLE")
    List<Store> getall();

    @Query("Delete FROM STORE_TABLE")
    void deleteall();
}
