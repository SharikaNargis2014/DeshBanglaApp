package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.CartItemPostRepository;
import com.deshbangla.shrimpandfish.response.CartItemPostResponse;

public class CartItemPostViewModel extends ViewModel {

    private CartItemPostRepository cartItemPostRepository;

    public CartItemPostViewModel(){
        cartItemPostRepository = new CartItemPostRepository();
    }

    public LiveData<CartItemPostResponse> postCartItem(int id, int user_id, String image, String name, String price, String qty){
        return cartItemPostRepository.postCartItem(id, user_id, image, name, price, qty);
    }

}
