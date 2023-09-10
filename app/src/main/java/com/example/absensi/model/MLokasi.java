package com.example.absensi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MLokasi {@SerializedName("nameLokasi")
@Expose
private String nameLokasi;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

    public String getNameLokasi() {
        return nameLokasi;
    }

    public void setNameLokasi(String nameLokasi) {
        this.nameLokasi = nameLokasi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
