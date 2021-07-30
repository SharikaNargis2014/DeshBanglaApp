package com.deshbangla.shrimpandfish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItemUpdate {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
