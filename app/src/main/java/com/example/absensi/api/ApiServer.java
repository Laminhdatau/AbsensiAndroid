package com.example.absensi.api;

import com.example.absensi.database.Iauth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServer {
  //  public static final String base_url="https://restnode.cyclic.app/";
    public static final String base_url="http://192.168.44.51:3000/";
    private static Retrofit retrofit=null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
