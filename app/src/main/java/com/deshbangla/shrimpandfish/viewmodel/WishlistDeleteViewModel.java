package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.WishlistDeleteRepo;
import com.deshbangla.shrimpandfish.response.WishlistDeleteResponse;

public class WishlistDeleteViewModel extends ViewModel {

    private WishlistDeleteRepo wishlistDeleteRepo;

    public WishlistDeleteViewModel(){
        wishlistDeleteRepo = new WishlistDeleteRepo();
    }

    public LiveData<WishlistDeleteResponse> deleteWishlistItem(int id){
        return wishlistDeleteRepo.deleteWishlistItem(id);
    }

}
