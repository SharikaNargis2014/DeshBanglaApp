package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.UpdateQtyRepository;
import com.deshbangla.shrimpandfish.response.CartItemUpdateResponse;

public class UpdateQtyViewModel extends ViewModel {
    private UpdateQtyRepository updateQtyRepository;

    public UpdateQtyViewModel(){
        updateQtyRepository = new UpdateQtyRepository();
    }

    public LiveData<CartItemUpdateResponse> updateQty(int id, int user_id, String qty){
        return updateQtyRepository.updateQty(id, user_id, qty);
    }
}
