package com.deshbangla.shrimpandfish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignUpInfo {

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("password_confirmation")
    @Expose
    String passCon;

    public UserSignUpInfo(String name, String email, String password, String passCon) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passCon = passCon;
    }
}
