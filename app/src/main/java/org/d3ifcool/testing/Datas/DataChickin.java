package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataChickin {
    @SerializedName("id_chickin")
    String id_chickin;
    @SerializedName("type_produk")
    String type_produk;
    @SerializedName("populasi_masuk")
    String populasi_masuk;
    @SerializedName("kondisi_chick_in")
    String kondisi_chick_in;
    @SerializedName("harga_satuan")
    String harga_satuan;
    @SerializedName("jenis_produk")
    String jenis_produk;
    @SerializedName("tanggal_chickin")
    String tanggal_chickin;
    @SerializedName("id_kandang")
    String id_kandang;
    @SerializedName("periode")
    String periode;
    @SerializedName("created_at")
    String created_at;

        @SerializedName("total_ayam_saat_ini")
    private String total_ayam_saat_ini;

    @SerializedName("berat_doc")
    private String berat_doc;


    public String getId_chickin() {
        return id_chickin;
    }

    public void setId_chickin(String id_chickin) {
        this.id_chickin = id_chickin;
    }

    public String getType_produk() {
        return type_produk;
    }

    public void setType_produk(String type_produk) {
        this.type_produk = type_produk;
    }

    public String getPopulasi_masuk() {
        return populasi_masuk;
    }

    public void setPopulasi_masuk(String populasi_masuk) {
        this.populasi_masuk = populasi_masuk;
    }

    public String getKondisi_chick_in() {
        return kondisi_chick_in;
    }

    public void setKondisi_chick_in(String kondisi_chick_in) {
        this.kondisi_chick_in = kondisi_chick_in;
    }

    public String getHarga_satuan() {
        return harga_satuan;
    }

    public void setHarga_satuan(String harga_satuan) {
        this.harga_satuan = harga_satuan;
    }

    public String getJenis_produk() {
        return jenis_produk;
    }

    public void setJenis_produk(String jenis_produk) {
        this.jenis_produk = jenis_produk;
    }

    public String getTanggal_chickin() {
        return tanggal_chickin;
    }

    public void setTanggal_chickin(String tanggal_chickin) {
        this.tanggal_chickin = tanggal_chickin;
    }

    public String getId_kandang() {
        return id_kandang;
    }

    public void setId_kandang(String id_kandang) {
        this.id_kandang = id_kandang;
    }


    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTotal_ayam_saat_ini() {
        return total_ayam_saat_ini;
    }

    public void setTotal_ayam_saat_ini(String total_ayam_saat_ini) {
        this.total_ayam_saat_ini = total_ayam_saat_ini;
    }

    public String getBerat_doc() {
        return berat_doc;
    }

    public void setBerat_doc(String berat_doc) {
        this.berat_doc = berat_doc;
    }
}


