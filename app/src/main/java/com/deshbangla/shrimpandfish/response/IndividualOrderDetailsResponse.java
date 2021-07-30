package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.CustomerInfo;
import com.deshbangla.shrimpandfish.model.IndividualOderDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IndividualOrderDetailsResponse {

    @SerializedName("customer info")
    @Expose
    private CustomerInfo customerInfo;
    @SerializedName("oder details")
    @Expose
    private List<IndividualOderDetails> oderDetails = null;
    @SerializedName("order_total")
    @Expose
    private Integer orderTotal;

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public List<IndividualOderDetails> getOderDetails() {
        return oderDetails;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }
}
