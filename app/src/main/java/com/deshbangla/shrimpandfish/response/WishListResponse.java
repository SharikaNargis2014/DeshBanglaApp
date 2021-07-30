package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.WishListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WishListResponse {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("wishlist")
    @Expose
    private List<WishListItem> wishlist = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<WishListItem> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<WishListItem> wishlist) {
        this.wishlist = wishlist;
    }

}
