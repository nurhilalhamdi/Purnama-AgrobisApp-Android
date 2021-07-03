package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

public class MedisData {
    public String getId_medis() {
        return id_medis;
    }

    public void setId_medis(String id_medis) {
        this.id_medis = id_medis;
    }

    public String getNama_medis() {
        return nama_medis;
    }

    public void setNama_medis(String nama_medis) {
        this.nama_medis = nama_medis;
    }

    public String getDeskripsi_medis() {
        return deskripsi_medis;
    }

    public void setDeskripsi_medis(String deskripsi_medis) {
        this.deskripsi_medis = deskripsi_medis;
    }

    public String getGejala_medis() {
        return gejala_medis;
    }

    public void setGejala_medis(String gejala_medis) {
        this.gejala_medis = gejala_medis;
    }

    public String getFile_icon_medis() {
        return file_icon_medis;
    }

    public void setFile_icon_medis(String file_icon_medis) {
        this.file_icon_medis = file_icon_medis;
    }

    public String getImage_medis() {
        return image_medis;
    }

    public void setImage_medis(String image_medis) {
        this.image_medis = image_medis;
    }

    @SerializedName("id_medis")
    String id_medis;
    @SerializedName("nama_medis")
    String nama_medis;
    @SerializedName("deskripsi_medis")
    String deskripsi_medis;
    @SerializedName("gejala_medis")
    String gejala_medis;
    @SerializedName("file_icon_medis")
    String file_icon_medis;
    @SerializedName("image_medis")
    String image_medis;
}
