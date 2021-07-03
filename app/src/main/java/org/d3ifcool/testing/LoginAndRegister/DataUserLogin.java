package org.d3ifcool.testing.LoginAndRegister;

import com.google.gson.annotations.SerializedName;

public class DataUserLogin {

    @SerializedName("id_user")
    String id;


    public DataUserLogin() {
    }

    public DataUserLogin(String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
