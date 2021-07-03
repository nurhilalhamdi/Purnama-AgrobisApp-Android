package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataPerforma {
    @SerializedName("ayam_mati")
    String ayam_mati;
    @SerializedName("ayam_sakit")
    String ayam_sakit;
    @SerializedName("populasi_masuk")
    String populasi_masuk;
    @SerializedName("total_ayam_saat_ini")
    String total_ayam_saat_ini;
    @SerializedName("gagal_panen")
    String gagal_panen;
    @SerializedName("jumlah_panen")
    String jumlah_panen;
    @SerializedName("total_konsumsi_pakan")
    String total_konsumsi_pakan;
    @SerializedName("total_berat_keseluruhan")
    String total_berat_keseluruhan;
    @SerializedName("berat_doc")
    String berat_doc;
    @SerializedName("berat_panen_ekor")
    String berat_panen_ekor;
    @SerializedName("umur_panen")
    String umur_panen;

    public String getAyam_mati() {
        return ayam_mati;
    }

    public void setAyam_mati(String ayam_mati) {
        this.ayam_mati = ayam_mati;
    }

    public String getAyam_sakit() {
        return ayam_sakit;
    }

    public void setAyam_sakit(String ayam_sakit) {
        this.ayam_sakit = ayam_sakit;
    }

    public String getPopulasi_masuk() {
        return populasi_masuk;
    }

    public void setPopulasi_masuk(String populasi_masuk) {
        this.populasi_masuk = populasi_masuk;
    }

    public String getTotal_ayam_saat_ini() {
        return total_ayam_saat_ini;
    }

    public void setTotal_ayam_saat_ini(String total_ayam_saat_ini) {
        this.total_ayam_saat_ini = total_ayam_saat_ini;
    }

    public String getGagal_panen() {
        return gagal_panen;
    }

    public void setGagal_panen(String gagal_panen) {
        this.gagal_panen = gagal_panen;
    }

    public String getJumlah_panen() {
        return jumlah_panen;
    }

    public void setJumlah_panen(String jumlah_panen) {
        this.jumlah_panen = jumlah_panen;
    }

    public String getTotal_konsumsi_pakan() {
        return total_konsumsi_pakan;
    }

    public void setTotal_konsumsi_pakan(String total_konsumsi_pakan) {
        this.total_konsumsi_pakan = total_konsumsi_pakan;
    }

    public String getTotal_berat_keseluruhan() {
        return total_berat_keseluruhan;
    }

    public void setTotal_berat_keseluruhan(String total_berat_keseluruhan) {
        this.total_berat_keseluruhan = total_berat_keseluruhan;
    }

    public String getBerat_doc() {
        return berat_doc;
    }

    public void setBerat_doc(String berat_doc) {
        this.berat_doc = berat_doc;
    }

    public String getBerat_panen_ekor() {
        return berat_panen_ekor;
    }

    public void setBerat_panen_ekor(String berat_panen_ekor) {
        this.berat_panen_ekor = berat_panen_ekor;
    }

    public String getUmur_panen() {
        return umur_panen;
    }

    public void setUmur_panen(String umur_panen) {
        this.umur_panen = umur_panen;
    }
}

