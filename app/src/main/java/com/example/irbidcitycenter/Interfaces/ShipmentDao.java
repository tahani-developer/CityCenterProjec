package com.example.irbidcitycenter.Interfaces;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Activity.NewShipment;
import com.example.irbidcitycenter.Models.ReplacementModel;
import com.example.irbidcitycenter.Models.Shipment;
import com.example.irbidcitycenter.Models.ZoneModel;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface ShipmentDao {

    @Insert
    public long[] insertAll(List<Shipment> shipments);
    @Insert
    void insert(Shipment shipment);
    @Update
    void update(Shipment shipment);
    @Delete
    void delete(Shipment shipment);


    @Query("Delete from SHIPMENT_TABLE")
    void deleteALL();

    @Query ("select * from SHIPMENT_TABLE")
    LiveData<List<Shipment>> getallShipment();

    @Query("SELECT * FROM SHIPMENT_TABLE where ISPOSTED = :s")
   List<Shipment> getUnpostedShipment(String s);

    @Query("UPDATE SHIPMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    void updateShipmentPosted();
}
