package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("id_user")
    String id_user;
    @SerializedName("nama")
    String nama;
    @SerializedName("username")
    private String username;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("email")
    private String email;
    @SerializedName("no_telephone")
    private String no_telephone;
    @SerializedName("password")
    private String password;
    @SerializedName("status")
    private String status;
    @SerializedName("konfirmasi_password")
    private String konfirmasi_password;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telephone() {
        return no_telephone;
    }

    public void setNo_telephone(String no_telephone) {
        this.no_telephone = no_telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKonfirmasi_password() {
        return konfirmasi_password;
    }

    public void setKonfirmasi_password(String konfirmasi_password) {
        this.konfirmasi_password = konfirmasi_password;
    }
}
