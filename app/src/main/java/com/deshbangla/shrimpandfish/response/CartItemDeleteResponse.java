package com.deshbangla.shrimpandfish.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItemDeleteResponse {
    @SerializedName("cart")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
