package com.deshbangla.shrimpandfish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shipping_id")
    @Expose
    private Integer shippingId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("order_total")
    @Expose
    private Integer orderTotal;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Userphone")
    @Expose
    private Object userphone;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_num")
    @Expose
    private Integer phoneNum;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getId() {
        return id;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public Object getUserphone() {
        return userphone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public String getMessage() {
        return message;
    }
}
