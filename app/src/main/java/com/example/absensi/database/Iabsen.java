package com.example.absensi.database;

import com.example.absensi.model.MAbsen;
import com.example.absensi.model.MLokasi;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Iabsen {
    @GET("allhistory")
    Call<List<MAbsen>> getHistory();
    @GET("history/{id_user}")
    Call<List<MAbsen>> getAbsen(@Path("id_user") String idUser);

    @POST("absen")
    Call<Void> createAbsen(@Body RequestBody absenData);

    @GET("lokasi/{id_lokasi}")
    Call<MLokasi> getLokasiById(@Path("id_lokasi") String idLokasi);

}
