package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.StocktakeModel;
import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.ZoneReplashmentModel;

import java.util.List;

@Dao
public interface ZoneReplashmentDao {
    @Insert
    public long[] insertAll(List<ZoneReplashmentModel> zonereplashmentList);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ZoneReplashmentModel zonereplashmentList);

    @Delete
    public void delete(ZoneReplashmentModel zonereplashmentList);


    @Update
    public  void update(ZoneReplashmentModel zonereplashmentList);

    @Query("SELECT * FROM ZONEREPLASHMENT_TABLE WHERE ISPOSTED ='0' AND FromZone= :fromzone AND  ToZone= :tozone AND ItemCode= :itemcode")
    ZoneReplashmentModel getReplashment(String fromzone, String tozone, String itemcode);

     @Query("DELETE FROM ZONEREPLASHMENT_TABLE")
    public void deleteAll();

    @Query("UPDATE ZONEREPLASHMENT_TABLE SET Qty= :qty WHERE ISPOSTED ='0' AND FromZone= :fromzone AND  ToZone= :tozone AND ItemCode= :itemcode")
    public void updateqtyReplashment(String fromzone, String tozone, String itemcode,String qty);
    @Query ("select * from ZONEREPLASHMENT_TABLE WHERE ToZone= :zonecode AND ISPOSTED='0'")
    List<ZoneReplashmentModel> getzoneRows(String zonecode);

    @Query("SELECT * FROM ZONEREPLASHMENT_TABLE WHERE ISPOSTED='0'")
    public List<ZoneReplashmentModel>  getAllZonesUnposted();
    @Query("SELECT SUM(Qty) FROM ZONEREPLASHMENT_TABLE WHERE ToZone= :barcode AND ISPOSTED='0'")
    public int  GetQtyOfZone(String barcode);
    @Query("SELECT DISTINCT ToZone FROM ZONEREPLASHMENT_TABLE WHERE ISPOSTED='0'")
    public List<String> getZonesUnposted();
    @Query("DELETE FROM ZONEREPLASHMENT_TABLE WHERE ToZone= :barcode AND ISPOSTED='0'")
    public int  deletezonedata(String barcode);


    @Query("DELETE FROM ZONEREPLASHMENT_TABLE WHERE ToZone= :barcode AND ITEMCODE= :itemcode AND ISPOSTED='0'")
    public int  deleteITEM(String barcode,String itemcode);
    @Query("UPDATE ZONEREPLASHMENT_TABLE SET Qty = :qty WHERE ToZone= :ZONEbarcode AND ITEMCODE= :barcode AND ISPOSTED='0'" )
    int updateQTYreduced(String barcode,String qty,String ZONEbarcode);

    @Query("UPDATE ZONEREPLASHMENT_TABLE SET ISPOSTED = '1' WHERE ISPOSTED='0'" )
    int setposted();


    @Query("SELECT SUM(Qty) FROM ZONEREPLASHMENT_TABLE WHERE FromZone= :fromzone AND ItemCode= :itembarcode AND ISPOSTED='0'")
    public int  getQTYofItem(String fromzone,String itembarcode);
}
