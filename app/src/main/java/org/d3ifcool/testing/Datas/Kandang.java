package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class Kandang {
    @SerializedName("id_kandang")
    private String id;
    @SerializedName("alamat_kandang")
    private String alamatkandang;
    @SerializedName("kode_Kandang")
    private String kodekandang;
    @SerializedName("kode_blok")
    private String kodeblok;
    @SerializedName("jenis_kandang")
    private String jenis_kandang;


    public Kandang() {
    }

    public Kandang(String id, String kodekandang, String kodeblok, String jenis_kandang, String alamatkandang) {
        this.id = id;
        this.kodekandang = kodekandang;
        this.kodeblok = kodeblok;
        this.jenis_kandang = jenis_kandang;
        this.alamatkandang = alamatkandang;
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

    public String getJenis_kandang() {
        return jenis_kandang;
    }

    public void setJenis_kandang(String jenis_kandang) {
        this.jenis_kandang = jenis_kandang;
    }

    public String getAlamatkandang() {
        return alamatkandang;
    }

    public void setAlamatkandang(String alamatkandang) {
        this.alamatkandang = alamatkandang;
    }
}
