package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.WishListPostRepository;
import com.deshbangla.shrimpandfish.response.WishListPostResponse;

public class WishListPostViewModel extends ViewModel {

    private WishListPostRepository wishListPostRepository;

    public WishListPostViewModel(){
        wishListPostRepository = new WishListPostRepository();
    }

    public LiveData<WishListPostResponse> postWish(int id, int userId, int product_id){
        return wishListPostRepository.postWish(id, userId, product_id);
    }
}
