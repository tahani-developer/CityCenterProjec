package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.AllPOs;
import com.example.irbidcitycenter.Models.NewAllPOsInfo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewAllPOsInfoDao {

 @Insert
 public long[] insertAll(List<NewAllPOsInfo> allPOs);
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 public void insert(NewAllPOsInfo allPOs);

 @Delete
 public void delete(NewAllPOsInfo allPOs);

 @Query("SELECT PONO FROM NewAllPOsInfoTABLE")
 public List<String> getAll();
 @Query("DELETE FROM NewAllPOsInfoTABLE")
 public void deleteAll();

 @Query("SELECT BOXNO FROM NewAllPOsInfoTABLE where PONO= :po")
 public List<String> getboxs(String po);
 @Query("SELECT * FROM NewAllPOsInfoTABLE where PONO= :po and ITEMCODE = :itemcode")
 public NewAllPOsInfo getPOdetails(String po,String itemcode);



}
