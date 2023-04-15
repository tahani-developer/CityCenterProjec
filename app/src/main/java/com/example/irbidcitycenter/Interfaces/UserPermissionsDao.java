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

    @Query("SELECT * FROM UserPermissions_TABLE WHERE UserNO= :usernum AND UserPassword= :pass")
    UserPermissions getUserPermissions2(String usernum, String pass);


    @Query("DELETE FROM UserPermissions_TABLE")
    void deleteall();
    @Query("SELECT MasterUser FROM UserPermissions_TABLE WHERE UserActive='1' AND UserNO= :username AND UserPassword= :pass")
    String getIsMaster(String username,String pass);
    @Query("SELECT UserName FROM UserPermissions_TABLE WHERE UserNO= :usernum")
    String getUSERnAM(String usernum);


}
