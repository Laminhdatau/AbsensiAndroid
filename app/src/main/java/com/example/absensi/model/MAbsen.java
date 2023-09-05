package com.example.absensi.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MAbsen {
    String id_absen;
    String id_user;
    String nik;

    int id_jenis_Absen;
    int id_lokasi;
    String nama_lokasi;
    String latitude;
    String longitude;


    String jenis_absen;
    String nama_lengkap;
    String user_image;

    private Date time_absen_makassar;
    // Tambahkan getter dan setter untuk time_absen
    public Date getTime_absen() {
        return time_absen_makassar;
    }

    public void setTime_absen(String time_absen) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            this.time_absen_makassar = dateFormat.parse(time_absen);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getFormattedTime_absen() {
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", Locale.getDefault());
        return outputFormat.format(time_absen_makassar);
    }



    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }


    public String getId_absen() {
        return id_absen;
    }

    public void setId_absen(String id_absen) {
        this.id_absen = id_absen;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        nik = nik;
    }

    public int getId_jenis_Absen() {
        return id_jenis_Absen;
    }

    public void setId_jenis_Absen(int id_jenis_Absen) {
        this.id_jenis_Absen = id_jenis_Absen;
    }

    public int getId_lokasi() {
        return id_lokasi;
    }

    public void setId_lokasi(int id_lokasi) {
        this.id_lokasi = id_lokasi;
    }

    public String getNama_lokasi() {
        return nama_lokasi;
    }

    public void setNama_lokasi(String nama_lokasi) {
        this.nama_lokasi = nama_lokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getJenis_absen() {
        return jenis_absen;
    }

    public void setJenis_absen(String jenis_absen) {
        this.jenis_absen = jenis_absen;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }
}
