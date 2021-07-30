package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.DeleteCartItemRepository;
import com.deshbangla.shrimpandfish.response.CartItemDeleteResponse;

public class CartItemDeleteViewModel extends ViewModel {

    private DeleteCartItemRepository deleteCartItemRepository;

    public CartItemDeleteViewModel() {
        deleteCartItemRepository = new DeleteCartItemRepository();
    }

    public LiveData<CartItemDeleteResponse> deleteCartItem(int id){
        return deleteCartItemRepository.deleteCartItem(id);
    }

}
