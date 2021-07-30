package com.deshbangla.shrimpandfish.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistDeleteResponse {
    @SerializedName("wishlist")
    @Expose
    private String wishlist;

    public String getWishlist() {
        return wishlist;
    }
}
