package com.example.irbidcitycenter.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.irbidcitycenter.Models.ZoneModel;
import com.example.irbidcitycenter.Models.appSettings;

import java.util.List;
@Dao
public interface SettingDao {
    @Insert
    void insert(appSettings appSettings);
    @Update
    void update(appSettings appSettings);
    @Delete
    void delete(appSettings appSettings);
    @Query ("update SETTINGS_TABLE set  logoimage ='' ")
    void  updatelogo();


    @Query("Delete from SETTINGS_TABLE")
    void deleteALL();
@Ignore
    @Query ("select COMPANYNO,DEVICEID,IP_ADDRESS,USERNO,SERIALZONE from SETTINGS_TABLE")
    List<appSettings> getallsetting();

    @Query ("select IP_ADDRESS from SETTINGS_TABLE")
    String getIpAddress();
    @Query ("select COMPANYNO from SETTINGS_TABLE")
    String getCono();

    @Query ("update SETTINGS_TABLE set  YEARS =:coYear ")
    void  updateCompanyYear(String coYear);

    @Query ("update SETTINGS_TABLE set  COMPANYNO=:cono ")
    void  updateCompanyInfo(String cono);
    @Query ("update SETTINGS_TABLE set USERNO=:usnu ")
    void  updateusernum(String usnu);

    @Query ("select USERNO from SETTINGS_TABLE")
    String getUserNo();

    @Query ("update SETTINGS_TABLE set logoimage=:logo ")
    void  updatimage(byte[] logo);
    @Query ("update SETTINGS_TABLE set mainimage=:logo ")
    void  updatmainimage(byte[] logo);
    @Query ("select logoimage From SETTINGS_TABLE")
    byte[] getlogo();

    @Query ("select mainimage From SETTINGS_TABLE")
    byte[] getmainimage();

}
