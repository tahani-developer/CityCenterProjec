package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.ReplenishmentReverseModel;

import java.util.List;

@Dao
public interface RepReversDao {

    @Insert
    public long[] insertAll(List<ReplenishmentReverseModel> replacements);
    @Insert
    void insert( ReplenishmentReverseModel replacementModel);
    @Update
    void update( ReplenishmentReverseModel replacementModel);
    @Delete
    int delete( ReplenishmentReverseModel replacementModel);


    @Query("Delete from REPLACEMENTREVERSE_TABLE")
    void deleteALL();

    @Query ("select * from REPLACEMENTREVERSE_TABLE WHERE ISPOSTED='0'")
    List< ReplenishmentReverseModel>getallReplacement();
    @Query ("select * from REPLACEMENTREVERSE_TABLE WHERE ISPOSTED='0'AND ZONECODE= :Zone")
    List< ReplenishmentReverseModel>getzoneReplacement(String Zone);
    @Query("SELECT * FROM REPLACEMENTREVERSE_TABLE where ISPOSTED = :s")
    List< ReplenishmentReverseModel> getUnpostedReplacement(String s);


    @Query("UPDATE REPLACEMENTREVERSE_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateReplashmentPosted();
    @Query("UPDATE REPLACEMENTREVERSE_TABLE SET RECQTY = :qty WHERE ITEMCODE= :barcode AND ISPOSTED='0' AND FROMSTORE= :FromNo" )
    int updateQTY(String FromNo,String barcode,String qty);

    @Query("DELETE FROM REPLACEMENTREVERSE_TABLE WHERE ITEMCODE= :barcode AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ISPOSTED='0'")
    int  deleteReplacement(String barcode,String FrSt,String ToSt);

    @Query("SELECT * FROM REPLACEMENTREVERSE_TABLE WHERE ITEMCODE = :s AND FROMSTORE= :FrSt AND TOSTORE= :ToSt AND ISPOSTED='0'")
    ReplenishmentReverseModel getReplacement(String s,String FrSt,String ToSt);

    @Query("Delete from REPLACEMENTREVERSE_TABLE WHERE TOSTORE= :ToSt AND ZONECODE= :Zone ")
    int deletezone(String Zone,String ToSt);

    @Query("DELETE FROM REPLACEMENTREVERSE_TABLE WHERE ITEMCODE= :itemcode AND FROMSTORE= :St AND ISPOSTED='0'")
    int  deleteDbReplacement(String itemcode,String St);
    @Query("UPDATE REPLACEMENTREVERSE_TABLE SET RECQTY = :qty WHERE  ITEMCODE= :itemcode AND FROMSTORE= :St AND ISPOSTED='0'" )
    int updateDBQTY(String qty,String itemcode,String St);

}
