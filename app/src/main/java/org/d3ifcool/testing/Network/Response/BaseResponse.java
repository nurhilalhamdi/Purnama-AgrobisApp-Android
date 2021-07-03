package org.d3ifcool.testing.Network.Response;

import com.google.gson.annotations.SerializedName;

import org.d3ifcool.testing.Datas.Kandang;
import org.d3ifcool.testing.LoginAndRegister.DataUserLogin;

import java.util.List;

public class BaseResponse {
    public class BaseResponseApi<T> {
        @SerializedName("error")
        private boolean error;
        @SerializedName("message")
        private String message;
        @SerializedName("data")
        private T data;

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
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

    public class GetUserLogin{

        @SerializedName("status")
        String status;
        @SerializedName("result")
        List<DataUserLogin> listdatauserlogin;

        @SerializedName("id_user")
        String id;
        @SerializedName("message")
        String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<DataUserLogin> getListdatauserlogin() {
            return listdatauserlogin;
        }

        public void setListdatauserlogin(List<DataUserLogin> listdatauserlogin) {
            this.listdatauserlogin = listdatauserlogin;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
