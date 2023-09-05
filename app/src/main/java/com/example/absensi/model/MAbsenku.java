package com.example.absensi.model;

public class MAbsenku {
    String id_absen;
    String id_user;
    int id_lokasi;
    int id_jenis_absen;

    String time_absen;
    String time_absen_makassar;

    public String getTime_absen() {
        return time_absen;
    }
    public void setTime_absen(String time_absen) {
        this.time_absen = time_absen;
    }


    public String getTime_absen_makassar() {
        return time_absen_makassar;
    }

    public void setTime_absen_makassar(String time_absen_makassar) {
        this.time_absen_makassar = time_absen_makassar;
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

    public int getId_lokasi() {
        return id_lokasi;
    }

    public void setId_lokasi(int id_lokasi) {
        this.id_lokasi = id_lokasi;
    }

    public int getId_jenis_absen() {
        return id_jenis_absen;
    }

    public void setId_jenis_absen(int id_jenis_absen) {
        this.id_jenis_absen = id_jenis_absen;
    }



}
