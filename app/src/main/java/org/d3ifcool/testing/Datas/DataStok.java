package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class DataStok {
    @SerializedName("id_barang")
    String id_barang;
    @SerializedName ("kode")
    String kode;
    @SerializedName("nama")
    String nama;
    @SerializedName("stok")
    int stok;
    @SerializedName("deskripsi")
    String deskripsi;
    @SerializedName("id_kategori")
    int id_kategori;
    @SerializedName("id_satuan")
    int id_satuan;
    @SerializedName("harga")
    String harga;
    @SerializedName("modal")
    String modal;
    @SerializedName("kategori")
    String kategori;
    @SerializedName("satuan")
    String satuan;


    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public int getId_satuan() {
        return id_satuan;
    }

    public void setId_satuan(int id_satuan) {
        this.id_satuan = id_satuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}
