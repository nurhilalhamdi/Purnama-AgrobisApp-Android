package org.d3ifcool.testing.LoginAndRegister;

import com.google.gson.annotations.SerializedName;

import org.d3ifcool.testing.Data;

import java.util.List;

public class GetDataUserLogin {

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
