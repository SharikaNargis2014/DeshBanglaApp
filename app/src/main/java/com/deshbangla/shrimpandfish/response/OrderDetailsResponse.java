package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.OrderDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse {

    @SerializedName("orders")
    @Expose
    private List<OrderDetails> orders = null;

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }

}
