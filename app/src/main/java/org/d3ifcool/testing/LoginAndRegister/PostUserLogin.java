package org.d3ifcool.testing.LoginAndRegister;

import com.google.gson.annotations.SerializedName;

public class PostUserLogin {

    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    DataUserLogin dataUserLogin;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataUserLogin getDataUserLogin() {
        return dataUserLogin;
    }

    public void setDataUserLogin(DataUserLogin dataUserLogin) {
        this.dataUserLogin = dataUserLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
