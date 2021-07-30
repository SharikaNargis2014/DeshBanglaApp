package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.CartItemRepository;
import com.deshbangla.shrimpandfish.response.CartItemResponse;

public class CartItemViewModel extends ViewModel {

    private CartItemRepository cartItemRepository;

    public CartItemViewModel(){
        cartItemRepository = new CartItemRepository();
    }

    public LiveData<CartItemResponse> getCartItems(int id){
        return cartItemRepository.getCartItems(id);
    }
}
