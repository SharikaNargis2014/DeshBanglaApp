package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.CartItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartItemPostResponse {
    @SerializedName("Cart")
    @Expose
    private CartItem cart;

    public CartItem getCart() {
        return cart;
    }

    public void setCart(CartItem cart) {
        this.cart = cart;
    }
}
