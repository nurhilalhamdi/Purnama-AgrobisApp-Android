package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class Sampel {
    @SerializedName("id")
    private String id;
    @SerializedName("kode_kandang")
    private String kodekandang;
    @SerializedName("kode_blok")
    private String kodeblok;
    @SerializedName("jumlah_ayam")
    private String jumlah_ayam;

    public Sampel(){}

    public Sampel(String id, String kodekandang, String kodeblok, String jumlah_ayam) {
        this.id = id;
        this.kodekandang = kodekandang;
        this.kodeblok = kodeblok;
        this.jumlah_ayam = jumlah_ayam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodekandang() {
        return kodekandang;
    }

    public void setKodekandang(String kodekandang) {
        this.kodekandang = kodekandang;
    }

    public String getKodeblok() {
        return kodeblok;
    }

    public void setKodeblok(String kodeblok) {
        this.kodeblok = kodeblok;
    }

    public String getJumlah_ayam() {
        return jumlah_ayam;
    }

    public void setJumlah_ayam(String jumlah_ayam) {
        this.jumlah_ayam = jumlah_ayam;
    }
}
