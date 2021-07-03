package org.d3ifcool.testing;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    String id;

    @SerializedName("tanggal_waktu_cek")
    String TanggalWaktuCek;

    @SerializedName("nama_petugas")
    String NamaPetugas;

    @SerializedName("alamat_kandang")
    String AlamatKandang;

    @SerializedName("kode_kandang")
    String KodeKandang;

    @SerializedName("kode_blok")
    String KodeBlok;

    @SerializedName("jumlah_ayam")
    String JumlahAyam;

    @SerializedName("umur_ayam")
    String UmurAyam;

    @SerializedName("tanggal_ayam_masuk")
    String TanggalAyamMasuk;

    @SerializedName("kondisi_ayam")
    String KondisiAyam;

    @SerializedName("jumlah_ayam_sakit")
    String JumlahAyamSakit;

    @SerializedName("gejala_sakit")
    String GejalaSakit;

    @SerializedName("jam_pakan")
    String JamPakan;

    @SerializedName("jam_ganti_minum")
    String JamGantiMinum;

    @SerializedName("jam_ganti_vitamin")
    String JamGantiVitamin;

    @SerializedName("jumlah_pakan")
    String JumlahPakan;

    @SerializedName("latitude")
    String Latitude;

    @SerializedName("longitude")
    String Longitude;

    @SerializedName("id_user")
    String IdUser;


    public Data(String id,
                String tanggalWaktuCek,
                String namaPetugas,
                String alamatKandang,
                String kodeKandang,
                String kodeBlok,
                String jumlahAyam,
                String umurAyam,
                String tanggalAyamMasuk,
                String kondisiAyam,
                String jumlahAyamSakit,
                String gejalaSakit,
                String jamPakan,
                String jamGantiMinum,
                String jamGantiVitamin,
                String jumlahPakan,
                String latitude,
                String longitude,
                String idUser) {

        this.id = id;
        TanggalWaktuCek = tanggalWaktuCek;
        NamaPetugas = namaPetugas;
        AlamatKandang = alamatKandang;
        KodeKandang = kodeKandang;
        KodeBlok = kodeBlok;
        JumlahAyam = jumlahAyam;
        UmurAyam = umurAyam;
        TanggalAyamMasuk = tanggalAyamMasuk;
        KondisiAyam = kondisiAyam;
        JumlahAyamSakit = jumlahAyamSakit;
        GejalaSakit = gejalaSakit;
        JamPakan = jamPakan;
        JamGantiMinum = jamGantiMinum;
        JamGantiVitamin = jamGantiVitamin;
        JumlahPakan = jumlahPakan;
        Latitude = latitude;
        Longitude = longitude;
        IdUser = idUser;
    }

    public Data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggalWaktuCek() {
        return TanggalWaktuCek;
    }

    public void setTanggalWaktuCek(String tanggalWaktuCek) {
        TanggalWaktuCek = tanggalWaktuCek;
    }

    public String getNamaPetugas() {
        return NamaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        NamaPetugas = namaPetugas;
    }

    public String getAlamatKandang() {
        return AlamatKandang;
    }

    public void setAlamatKandang(String alamatKandang) {
        AlamatKandang = alamatKandang;
    }

    public String getKodeKandang() {
        return KodeKandang;
    }

    public void setKodeKandang(String kodeKandang) {
        KodeKandang = kodeKandang;
    }

    public String getKodeBlok() {
        return KodeBlok;
    }

    public void setKodeBlok(String kodeBlok) {
        KodeBlok = kodeBlok;
    }

    public String getJumlahAyam() {
        return JumlahAyam;
    }

    public void setJumlahAyam(String jumlahAyam) {
        JumlahAyam = jumlahAyam;
    }

    public String getUmurAyam() {
        return UmurAyam;
    }

    public void setUmurAyam(String umurAyam) {
        UmurAyam = umurAyam;
    }

    public String getTanggalAyamMasuk() {
        return TanggalAyamMasuk;
    }

    public void setTanggalAyamMasuk(String tanggalAyamMasuk) {
        TanggalAyamMasuk = tanggalAyamMasuk;
    }

    public String getKondisiAyam() {
        return KondisiAyam;
    }

    public void setKondisiAyam(String kondisiAyam) {
        KondisiAyam = kondisiAyam;
    }

    public String getJumlahAyamSakit() {
        return JumlahAyamSakit;
    }

    public void setJumlahAyamSakit(String jumlahAyamSakit) {
        JumlahAyamSakit = jumlahAyamSakit;
    }

    public String getGejalaSakit() {
        return GejalaSakit;
    }

    public void setGejalaSakit(String gejalaSakit) {
        GejalaSakit = gejalaSakit;
    }

    public String getJamPakan() {
        return JamPakan;
    }

    public void setJamPakan(String jamPakan) {
        JamPakan = jamPakan;
    }

    public String getJamGantiMinum() {
        return JamGantiMinum;
    }

    public void setJamGantiMinum(String jamGantiMinum) {
        JamGantiMinum = jamGantiMinum;
    }

    public String getJamGantiVitamin() {
        return JamGantiVitamin;
    }

    public void setJamGantiVitamin(String jamGantiVitamin) {
        JamGantiVitamin = jamGantiVitamin;
    }

    public String getJumlahPakan() {
        return JumlahPakan;
    }

    public void setJumlahPakan(String jumlahPakan) {
        JumlahPakan = jumlahPakan;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }
}
