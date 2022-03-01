package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.CompanyInfo;

import java.util.List;

@Dao
public interface CompanyDao {
    @Insert
    public long[] insertAll(List<CompanyInfo> allItems);

    @Query("SELECT * FROM Company_TABLE")
    List<CompanyInfo>   getAll();

    @Query("Delete FROM Company_TABLE")
    void deleteall();

    @Query("SELECT CoNameA FROM Company_TABLE WHERE CoNo= :No")
    String getcomName(String No);

}
