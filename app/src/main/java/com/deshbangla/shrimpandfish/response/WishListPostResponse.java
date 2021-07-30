package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.WishListPostItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishListPostResponse {

    @SerializedName("wishlist")
    @Expose
    private WishListPostItem wishlist;

    public WishListPostItem getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishListPostItem wishlist) {
        this.wishlist = wishlist;
    }

}
