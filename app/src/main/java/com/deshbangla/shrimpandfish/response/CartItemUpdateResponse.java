package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.CartItemUpdate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItemUpdateResponse {
    @SerializedName("cart")
    @Expose
    private CartItemUpdate cart;

    public CartItemUpdate getCart() {
        return cart;
    }

    public void setCart(CartItemUpdate cart) {
        this.cart = cart;
    }
}
