package com.example.irbidcitycenter.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Activity.Replacement;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;

import java.util.List;
@Dao
public interface ReplacementDao {
    @Insert
    public long[] insertAll(List<ReplacementModel> replacements);
    @Insert
    void insert( ReplacementModel replacementModel);
    @Update
    void update( ReplacementModel replacementModel);
    @Delete
    int delete( ReplacementModel replacementModel);


    @Query("Delete from REPLACEMENT_TABLE")
    void deleteALL();

    @Query ("select * from REPLACEMENT_TABLE WHERE ISPOSTED='0'")
    List<ReplacementModel>getallReplacement();
    @Query ("select * from REPLACEMENT_TABLE WHERE ISPOSTED='0'AND ZONECODE= :Zone")
    List<ReplacementModel>getzoneReplacement(String Zone);
    @Query("SELECT * FROM REPLACEMENT_TABLE where ISPOSTED = :s")
    List<ReplacementModel> getUnpostedReplacement(String s);


    @Query("UPDATE REPLACEMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateReplashmentPosted();
    @Query("UPDATE REPLACEMENT_TABLE SET RECQTY = :qty WHERE ITEMCODE= :barcode AND ISPOSTED='0'" )
    int updateQTY(String barcode,String qty);

    @Query("DELETE FROM REPLACEMENT_TABLE WHERE ITEMCODE= :barcode AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ISPOSTED='0'")
    int  deleteReplacement(String barcode,String FrSt,String ToSt);

    @Query("SELECT * FROM REPLACEMENT_TABLE WHERE ITEMCODE = :s AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ZONECODE= :Zone AND ISPOSTED='0'")
    ReplacementModel getReplacement(String s,String Zone,String FrSt,String ToSt);

    @Query("Delete from REPLACEMENT_TABLE WHERE TOSTORE= :ToSt AND ZONECODE= :Zone ")
   int deletezone(String Zone,String ToSt);

    @Query("DELETE FROM REPLACEMENT_TABLE WHERE ITEMCODE= :itemcode AND ZONECODE= :zonecode AND TOSTORE= :ToSt AND ISPOSTED='0'")
    int  deleteDbReplacement(String zonecode,String itemcode,String ToSt);
    @Query("UPDATE REPLACEMENT_TABLE SET RECQTY = :qty WHERE  ITEMCODE= :itemcode AND ZONECODE= :zonecode AND TOSTORE= :ToSt AND ISPOSTED='0'" )
    int updateDBQTY(String qty,String zonecode,String itemcode,String ToSt);

}
