package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;

import java.util.List;
@Dao
public interface StocktakeDao {


    @Insert
    public long[] insertAll(List<ZoneModel> zones);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(StocktakeModel stocktakeModel);

    @Delete
    public void delete(StocktakeModel stocktakeModel);


    @Update
    public  void update(StocktakeModel stocktakeModel);

    @Query("DELETE FROM STOCKTAKE_TABLE")
    void deleteAll();
@Query("UPDATE STOCKTAKE_TABLE SET QTY= :qty WHERE ZONECODE= :zonecode AND ITEMOCODE= :itemcode AND STORE= :store AND ISPOSTED ='0'")
   int   IncreasQty (String qty,String zonecode,String itemcode,String store);

    @Query("SELECT * FROM STOCKTAKE_TABLE WHERE ISPOSTED ='0'")
List<StocktakeModel>getall();

    @Query("SELECT * FROM STOCKTAKE_TABLE WHERE ZONECODE= :zonecode AND ITEMOCODE= :itemcode AND STORE= :store AND ISPOSTED ='0'")
  StocktakeModel  getstockObj(String store,String zonecode,String itemcode);

    @Query("Delete from STOCKTAKE_TABLE WHERE STORE= :ToSt AND ZONECODE= :Zone AND ISPOSTED ='0'")
    int deletezone(String Zone,String ToSt);
    @Query("Delete from STOCKTAKE_TABLE WHERE STORE= :ToSt AND ZONECODE= :Zone AND ITEMOCODE= :item AND ISPOSTED ='0'")
    int deletestockObj(String Zone,String item,String ToSt);

    @Query("UPDATE STOCKTAKE_TABLE SET QTY= :qty WHERE ZONECODE= :zonecode AND ITEMOCODE= :itemcode AND STORE= :store AND ISPOSTED='0'")
    int   UpdateQty (String qty,String zonecode,String itemcode,String store);

    @Query("UPDATE STOCKTAKE_TABLE SET ISPOSTED= :poststate WHERE ISPOSTED='0'")
    void setposted(String poststate);

    @Query ("select SUM(CAST(QTY AS LONG)) from STOCKTAKE_TABLE WHERE STORE= :Store AND ISPOSTED='0'")
long getStoreQty(String Store);
    @Query ("select SUM(CAST(QTY AS LONG)) from STOCKTAKE_TABLE WHERE ZONECODE= :zonecode AND ISPOSTED='0'")
    long getZoneQty(String zonecode);

    @Query ("select SUM(CAST(QTY AS LONG)) from STOCKTAKE_TABLE WHERE ISPOSTED='0'")
    long  getItemQty();
    @Query("SELECT * FROM STOCKTAKE_TABLE WHERE DATE= :date AND ISPOSTED='0'")
    List<StocktakeModel> getdateStoketakes(String date);

    @Query("SELECT DISTINCT STORE FROM STOCKTAKE_TABLE")
    List<String> getStoresNo();

    @Query("SELECT DISTINCT StoreName FROM STOCKTAKE_TABLE")
    List<String> getStoresNames();
}
