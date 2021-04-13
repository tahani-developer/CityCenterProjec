package com.example.irbidcitycenter.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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


    @Query("Delete from SETTINGS_TABLE")
    void deleteALL();

    @Query ("select * from SETTINGS_TABLE")
    List<appSettings> getallsetting();

    @Query ("select IP_ADDRESS from SETTINGS_TABLE")
    String getIpAddress();
    @Query ("select COMPANYNO from SETTINGS_TABLE")
    String getCono();

}
