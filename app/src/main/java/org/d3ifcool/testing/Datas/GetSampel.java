package org.d3ifcool.testing.Datas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSampel {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Sampel> listDataSampel;
    @SerializedName("message")
    String message;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<Sampel> getListDataKontak() {
        return listDataSampel;
    }
    public void setListDataKontak(List<Sampel> listDataSampel) {
        this.listDataSampel = listDataSampel;
    }
}
