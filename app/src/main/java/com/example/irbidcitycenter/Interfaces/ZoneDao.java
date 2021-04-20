package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ZoneModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ZoneDao {
    @Insert
   public long[] insertAll(List<ZoneModel> zones);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(ZoneModel zones);

    @Delete
    public void delete(ZoneModel item);


    @Update
    public  void update(ZoneModel item);

    @Query("DELETE FROM ZONETABLE")
    void deleteAll();


    @Query("SELECT * FROM ZONETABLE ")
    public List<ZoneModel> getAllZones();

    @Query("SELECT * FROM ZONETABLE where  SERIALZONE IN(:Itemid) ")
    public  List<ZoneModel> getZonebyId(int Itemid);

// @Query("update  ZONETABLE  set ISPOSTED =1 where  ZONECODE = zoneCode ")
// public  void updateZoneIsPosted(String zoneCode);


 @Query("SELECT * FROM ZONETABLE where ISPOSTED = :posted")
 public  List<ZoneModel> getUnpostedZone(String posted);

 @Query("SELECT * FROM ZONETABLE ")
 public  List<ZoneModel> getUnpostedZone2();

 @Query("UPDATE ZONETABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
  public   void updateZonePosted();
}
