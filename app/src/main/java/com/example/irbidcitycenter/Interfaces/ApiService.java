package com.example.irbidcitycenter.Interfaces;

import com.example.irbidcitycenter.Models.AllItems;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


 @GET("IrGetAllItems")
 Call<List<AllItems>> gatItemInfoDetail(@Query("CONO") String ComNo);

 @GET("IrGetAllItems")
 Observable<List<AllItems>> gatItemInfoDetail2(@Query("CONO") String ComNo);

}
