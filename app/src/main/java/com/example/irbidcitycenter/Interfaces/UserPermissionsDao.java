package com.example.irbidcitycenter.Interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.irbidcitycenter.Models.AllItems;
import com.example.irbidcitycenter.Models.UserPermissions;

import java.util.List;

@Dao
public interface UserPermissionsDao  {
    @Insert
    public long[] insertAll(List<UserPermissions> allItems);

    @Query("SELECT * FROM UserPermissions_TABLE")
    List<UserPermissions>   getAll();

    @Query("SELECT * FROM UserPermissions_TABLE WHERE UserNO= :usernum")
    UserPermissions getUserPermissions(String usernum);
    @Query("DELETE FROM UserPermissions_TABLE")
    void deleteall();
}
