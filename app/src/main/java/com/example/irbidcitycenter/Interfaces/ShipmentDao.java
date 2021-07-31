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

import static com.example.irbidcitycenter.Activity.NewShipment.barcodeofrow;

@Dao
public interface ShipmentDao {

    @Insert
    public long[] insertAll(List<Shipment> shipments);
    @Insert
    void insert(Shipment shipment);
    @Update
    int update(Shipment shipment);
    @Delete
    void delete(Shipment shipment);


    @Query("Delete from SHIPMENT_TABLE")
    void deleteALL();

    @Query ("select * from SHIPMENT_TABLE")
   List<Shipment>getallShipment();

    @Query("SELECT * FROM SHIPMENT_TABLE where ISPOSTED = :s")
   List<Shipment> getUnpostedShipment(String s);

    @Query("UPDATE SHIPMENT_TABLE SET  ISPOSTED='1' WHERE ISPOSTED='0' ")
    int updateShipmentPosted();

    @Query("UPDATE SHIPMENT_TABLE SET  QTY = :qty ,Differ= :dif WHERE BARECODE= :barcode AND PONO= :po AND BOXNO= :box AND ISPOSTED='0'" )
    int updateQTY(String barcode,String po,String box, String qty,String dif);


    @Query ("select * from SHIPMENT_TABLE WHERE BARECODE= :barcode AND PONO= :po AND BOXNO= :box AND ISPOSTED='0'")
   Shipment getShipments(String barcode,String po,String box);
    @Query ("select * from SHIPMENT_TABLE WHERE BARECODE= :barcode AND PONO= :po AND BOXNO= :box")
    List<Shipment> getNEWShipments(String barcode,String po,String box);

    @Query ("select * from SHIPMENT_TABLE WHERE SERIAL= (SELECT MAX(SERIAL) FROM SHIPMENT_TABLE) AND ISPOSTED='0' AND PONO= :po")
 Shipment getlastShipment(String po);

    @Query ("select * from SHIPMENT_TABLE WHERE SERIAL= (SELECT MAX(SERIAL) FROM SHIPMENT_TABLE)")
    Shipment getlastbox();

    @Query ("select * from SHIPMENT_TABLE WHERE PONO= :po")
    List<Shipment> getPOShipments(String po);

    @Query ("select SUM(QTY) from SHIPMENT_TABLE WHERE PONO= :po")
   int getsumofqty(String po);
    @Query ("select SUM(QTY) from SHIPMENT_TABLE WHERE BOXNO= :box AND PONO= :po")
    int getsumofboxitemsqty(String po,String box);

    @Query("Delete from SHIPMENT_TABLE WHERE BARECODE= :barcode AND PONO= :po AND BOXNO= :box AND ISPOSTED='0'")
    void deleteshipment(String barcode,String po,String box);
    @Query ("select BOXNO from SHIPMENT_TABLE")
    List<String>getboxes();


    @Query("SELECT DISTINCT PONO FROM  SHIPMENT_TABLE WHERE ISPOSTED='0'")
    public List<String> getallpo();
    @Query ("select  Count(DISTINCT BOXNO) from SHIPMENT_TABLE  WHERE ISPOSTED='0' AND PONO= :po")
    String getboxescount(String po);
    @Query ("select SUM(QTY) from SHIPMENT_TABLE WHERE PONO= :po AND ISPOSTED='0'")
    String getsumofitemsqty(String po);
    @Query("Delete from SHIPMENT_TABLE WHERE PONO= :po AND ISPOSTED='0'")
    void deletePO(String po);

    @Query("Delete from SHIPMENT_TABLE WHERE PONO= :po AND BOXNO= :box AND ISPOSTED='0'")
    int deleteBox(String po,String box);
    @Query ("select * from SHIPMENT_TABLE WHERE PONO= :po AND BOXNO= :box AND ISPOSTED='0'")
    List<Shipment> getBoxsShipments(String po,String box);
}
