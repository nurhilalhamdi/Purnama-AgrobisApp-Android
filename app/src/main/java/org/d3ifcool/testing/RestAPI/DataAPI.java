package org.d3ifcool.testing.RestAPI;

import com.google.gson.annotations.SerializedName;

import org.d3ifcool.testing.Data;
import org.d3ifcool.testing.Datas.Kandang;


import java.util.List;

public class DataAPI {

    public class loginuser{
        @SerializedName("status")
        String status;
        @SerializedName("result")
        Data data;
       @SerializedName("message")
        String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Data getmHarian() {
            return data;
        }

        public void setmHarian(Data data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class user {
        @SerializedName("response")
        private String response;
        @SerializedName("nama")
        private String nama;

        @SerializedName("id_user")
        private String id;

        public String getResponse() {
            return response;
        }

        public String getNama() {
            return nama;
        }

        public String getId() {
            return id;
        }
    }

    public class kandangApi{
        @SerializedName("status")
        String status;
        @SerializedName("result")
        List<Kandang> listDataKandang;
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
        public List<Kandang> getListDataKandang() {
            return listDataKandang;
        }
        public void setListDataKontak(List<Kandang> listDataKandang) {
            this.listDataKandang = listDataKandang;
        }
    }
}
