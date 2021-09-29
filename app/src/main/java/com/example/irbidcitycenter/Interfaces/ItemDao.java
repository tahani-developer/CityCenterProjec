package com.example.irbidcitycenter.Interfaces;

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
}
