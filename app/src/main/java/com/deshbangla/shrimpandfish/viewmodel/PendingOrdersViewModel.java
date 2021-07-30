package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.PendingOrdersRepository;
import com.deshbangla.shrimpandfish.response.OrderDetailsResponse;

public class PendingOrdersViewModel extends ViewModel {

    private PendingOrdersRepository pendingOrdersRepository;

    public PendingOrdersViewModel(){
        pendingOrdersRepository = new PendingOrdersRepository();
    }

    public LiveData<OrderDetailsResponse> getPendingOrders(int id){
        return pendingOrdersRepository.getPendingOrders(id);
    }

}
