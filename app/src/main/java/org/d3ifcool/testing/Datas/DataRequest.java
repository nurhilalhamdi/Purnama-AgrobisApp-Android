package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRequest {

    @SerializedName("id")
    private String id;
    @SerializedName("nomor_request")
    private String nomor_request;
    @SerializedName("jumlah_request")
    private String jumlah_request;
    @SerializedName("catatan")
    private String catatan;
    @SerializedName("nama")
    private String nama;
    @SerializedName("id_status")
    private String id_status;
    @SerializedName("status")
    private String status;
    @SerializedName("satuan")
    private String satuan;
    @SerializedName("kode")
    private String kode;
    @SerializedName("tanggal_request")
    private String tanggal_request;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("keterangan")
    private String keterangan;


    public String getNomor_request() {
        return nomor_request;
    }

    public void setNomor_request(String nomor_request) {
        this.nomor_request = nomor_request;
    }

    public String getJumlah_request() {
        return jumlah_request;
    }

    public void setJumlah_request(String jumlah_request) {
        this.jumlah_request = jumlah_request;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getTanggal_request() {
        return tanggal_request;
    }

    public void setTanggal_request(String tanggal_request) {
        this.tanggal_request = tanggal_request;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
