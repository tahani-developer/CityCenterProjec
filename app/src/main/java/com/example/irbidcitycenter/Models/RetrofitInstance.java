package com.example.irbidcitycenter.Models;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
   private static Retrofit Instance;
   public static Retrofit getInstance(String BASE_URL) {
      Log.e("BASE_URL",BASE_URL);
      OkHttpClient client = new OkHttpClient().newBuilder()
              .readTimeout(
                      30, TimeUnit.MINUTES)
              .connectTimeout(30, TimeUnit.MINUTES).build();

      if (Instance == null)
         Instance = new Retrofit.Builder()
                 .baseUrl(BASE_URL+"/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .client(client)    .build();
      return Instance;
   }
   //
   private RetrofitInstance() {

   }
}
