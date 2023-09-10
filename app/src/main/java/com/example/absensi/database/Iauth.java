package com.example.absensi.database;

import com.example.absensi.model.auth.MRegister;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface Iauth {
    @Multipart
    @POST("/register")
    Call<MRegister> registerUser(
            @Part("username") RequestBody username,
            @Part("nama_lengkap") RequestBody namaNengkap,
            @Part("password") RequestBody password,
            @Part("nik") RequestBody nik,
            @Part MultipartBody.Part userImage
    );

    @FormUrlEncoded
    @POST("/login")
    Call<MRegister> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
