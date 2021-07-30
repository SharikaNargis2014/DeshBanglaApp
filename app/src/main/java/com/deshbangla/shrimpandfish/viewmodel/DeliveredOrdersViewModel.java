package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.DeliveredOrdersRepository;
import com.deshbangla.shrimpandfish.response.OrderDetailsResponse;

public class DeliveredOrdersViewModel extends ViewModel {

    private DeliveredOrdersRepository deliveredOrdersRepository;

    public DeliveredOrdersViewModel(){
        deliveredOrdersRepository = new DeliveredOrdersRepository();
    }

    public LiveData<OrderDetailsResponse> getDeliveredOrders(int id){
        return deliveredOrdersRepository.getDeliveredOrders(id);
    }

}
