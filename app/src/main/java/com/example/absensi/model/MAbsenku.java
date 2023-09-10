package com.example.absensi.model;

import android.provider.CallLog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MAbsenku {

    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_jenis_absen")
    @Expose
    private Integer idJenisAbsen;
    @SerializedName("id_lokasi")
    @Expose
    private String idLokasi;
    @SerializedName("time_absen")
    @Expose
    private String timeAbsen;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitde) {
        this.latitude = latitde;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getIdJenisAbsen() {
        return idJenisAbsen;
    }

    public void setIdJenisAbsen(Integer idJenisAbsen) {
        this.idJenisAbsen = idJenisAbsen;
    }

    public String getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(String idLokasi) {
        this.idLokasi = idLokasi;
    }

    public String getTimeAbsen() {
        return timeAbsen;
    }

    public void setTimeAbsen(String timeAbsen) {
        this.timeAbsen = timeAbsen;
    }

}
