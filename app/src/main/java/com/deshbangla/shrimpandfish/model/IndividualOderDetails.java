package com.deshbangla.shrimpandfish.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndividualOderDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("shipping_id")
    @Expose
    private Integer shippingId;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("order_total")
    @Expose
    private Integer orderTotal;
    @SerializedName("product_price")
    @Expose
    private Integer productPrice;
    @SerializedName("product_quantity")
    @Expose
    private Integer productQuantity;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getOrderTotal() {
        return orderTotal;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
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
}
