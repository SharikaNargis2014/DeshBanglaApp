package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.ShippingInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShippingResponse {
    @SerializedName("shipping info")
    @Expose
    private ShippingInfo shippingInfo;

    public ShippingInfo getOrderDetail() {
        return shippingInfo;
    }

    public void setOrderDetail(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}
