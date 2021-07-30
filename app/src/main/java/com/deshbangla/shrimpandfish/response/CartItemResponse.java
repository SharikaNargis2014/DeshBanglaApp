package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.CartItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartItemResponse {

    @SerializedName("Cart item")
    @Expose
    private List<CartItem> cartItem = null;

    @SerializedName("Total")
    @Expose
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

}
