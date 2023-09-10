package com.example.absensi.model.auth;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MRegister {


        @SerializedName("id_user")
        @Expose
        private String idUser;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("nama_lengkap")
        @Expose
        private String namaLengkap;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("nik")
        @Expose
        private String nik;
        @SerializedName("user_image")
        @Expose
        private String userImage;

    @SerializedName("user")
    @Expose
    private MRegister user;

    public MRegister getUser() {
        return user;
    }

    public void setUser(MRegister user) {
        this.user = user;
    }

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNamaLengkap() {
            return namaLengkap;
        }

        public void setNamaLengkap(String namaLengkap) {
            this.namaLengkap = namaLengkap;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNik() {
            return nik;
        }

        public void setNik(String nik) {
            this.nik = nik;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

    }

