package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.ShippingRepository;
import com.deshbangla.shrimpandfish.response.ShippingResponse;

public class ShippingViewModel extends ViewModel {

    private ShippingRepository shippingRepository;

    public ShippingViewModel(){
        shippingRepository = new ShippingRepository();
    }

    public LiveData<ShippingResponse> postShippingInfo(int id, String customerName, String email, String phoneNum, String address, String message, int user_id){
        return shippingRepository.postShippingInfo(id, customerName, email, phoneNum, address, message, user_id);
    }

}
