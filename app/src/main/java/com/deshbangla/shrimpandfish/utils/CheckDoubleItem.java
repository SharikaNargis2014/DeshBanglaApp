package com.deshbangla.shrimpandfish.utils;

import com.deshbangla.shrimpandfish.model.CartItem;
import com.deshbangla.shrimpandfish.model.WishListItem;
import java.util.List;


public class CheckDoubleItem {

    public static Boolean checkWishlist(int product_id, List<WishListItem> wishList) {
        boolean Check = false;
        for (int i = 0; i < wishList.size(); i++) {
            if (wishList.get(i).getProductId().equals(product_id)) {
                Check = true;
            }
        }
        return Check;
    }

    public static Boolean checkCartItems(int productId, List<CartItem> cartItems){
        boolean Check = false;
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProductId().equals(productId)) {
                Check = true;
            }
        }
        return Check;
    }

}
