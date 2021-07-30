package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.WishListRepository;
import com.deshbangla.shrimpandfish.response.WishListResponse;

public class WishListViewModel extends ViewModel {

    private WishListRepository wishListRepository;

    public WishListViewModel(){
        wishListRepository = new WishListRepository();
    }

    public LiveData<WishListResponse> getWishList(int id){
        return wishListRepository.getWishList(id);
    }

}
