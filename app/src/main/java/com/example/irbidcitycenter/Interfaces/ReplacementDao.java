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
    void delete( ReplacementModel replacementModel);


    @Query("Delete from REPLACEMENT_TABLE")
    void deleteALL();

    @Query ("select * from REPLACEMENT_TABLE")
    LiveData<List<ReplacementModel>> getallReplacement();

    @Query("SELECT * FROM REPLACEMENT_TABLE where ISPOSTED = :s")
    List<ReplacementModel> getUnpostedReplacement(String s);

    @Query("UPDATE REPLACEMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateReplacmentPosted();

    @Query("UPDATE SHIPMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateReplashmentPosted();

}
