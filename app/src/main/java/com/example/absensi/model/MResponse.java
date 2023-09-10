package com.example.absensi.model;

public class MResponse {
    private String message;
    private MAbsen data;
    private MLokasi locations;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MAbsen getData() {
        return data;
    }

    public void setData(MAbsen data) {
        this.data = data;
    }

    public MLokasi getLocations() {
        return locations;
    }

    public void setLocations(MLokasi locations) {
        this.locations = locations;
    }
}
