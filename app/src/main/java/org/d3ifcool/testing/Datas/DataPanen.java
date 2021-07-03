package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataPanen {
    @SerializedName("id")
    String id;
    @SerializedName("umur_panen")
    String umur_panen;
    @SerializedName("periode")
    String periode;
    @SerializedName("tanggal_panen")
    String tanggal_panen;
    @SerializedName("berat_panen_ekor")
    String berat_panen_ekor;
    @SerializedName("jumlah_panen")
    String jumlah_panen;
    @SerializedName("total_berat_keseluruhan")
    String total_berat_keseluruhan;
    @SerializedName("gagal_panen")
    String gagal_panen;
    @SerializedName("id_user")
    String id_user;
    @SerializedName("id_kandang")
    String id_kandang;
    @SerializedName("id_chickin")
    String id_chickin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUmur_panen() {
        return umur_panen;
    }

    public void setUmur_panen(String umur_panen) {
        this.umur_panen = umur_panen;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getTanggal_panen() {
        return tanggal_panen;
    }

    public void setTanggal_panen(String tanggal_panen) {
        this.tanggal_panen = tanggal_panen;
    }

    public String getBerat_panen_ekor() {
        return berat_panen_ekor;
    }

    public void setBerat_panen_ekor(String berat_panen_ekor) {
        this.berat_panen_ekor = berat_panen_ekor;
    }

    public String getJumlah_panen() {
        return jumlah_panen;
    }

    public void setJumlah_panen(String jumlah_panen) {
        this.jumlah_panen = jumlah_panen;
    }

    public String getTotal_berat_keseluruhan() {
        return total_berat_keseluruhan;
    }

    public void setTotal_berat_keseluruhan(String total_berat_keseluruhan) {
        this.total_berat_keseluruhan = total_berat_keseluruhan;
    }

    public String getGagal_panen() {
        return gagal_panen;
    }

    public void setGagal_panen(String gagal_panen) {
        this.gagal_panen = gagal_panen;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }

    public String getId_chickin() {
        return id_chickin;
    }

    public void setId_chickin(String id_chickin) {
        this.id_chickin = id_chickin;
    }
}
