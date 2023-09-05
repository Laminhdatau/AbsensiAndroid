package com.example.absensi.database;

import com.example.absensi.model.MAbsen;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Iabsen {
    @GET("history")
    Call<List<MAbsen>> getAbsen();
    @POST("absen")
    Call<Void> createAbsen(@Body RequestBody absenData);

}
