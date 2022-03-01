package com.example.irbidcitycenter.Interfaces;

import android.content.ClipData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
@Dao
public interface ItemDao {
    @Insert
    public long[] insertAll(List<AllItems> allItems);

    @Query("SELECT * FROM ITEM_TABLE")
    List<AllItems>   getAll();
    @Query("SELECT * FROM ITEM_TABLE LIMIT 3")
    List<AllItems>   getAll2();
    @Query("Delete FROM ITEM_TABLE")
  void deleteall();
    @Query("SELECT * FROM ITEM_TABLE WHERE ITEMOCODE= :itemcode ")
  AllItems getitem(String itemcode);
    @Query("SELECT ITEMOCODE FROM ITEM_TABLE WHERE ITEMOCODE= :itemcode ")
    String getitem2(String itemcode);
    @Query("SELECT COUNT(*) FROM ITEM_TABLE")
    int getcount();



}
