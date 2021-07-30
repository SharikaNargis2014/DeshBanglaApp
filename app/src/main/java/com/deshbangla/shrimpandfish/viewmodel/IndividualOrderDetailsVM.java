package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.IndividualOrderDetailsRepo;
import com.deshbangla.shrimpandfish.response.IndividualOrderDetailsResponse;

public class IndividualOrderDetailsVM extends ViewModel {

    private IndividualOrderDetailsRepo individualOrderDetailsRepo;

    public IndividualOrderDetailsVM(){
        individualOrderDetailsRepo = new IndividualOrderDetailsRepo();
    }

    public LiveData<IndividualOrderDetailsResponse> getOrderDetails(int userId, int orderId){
        return individualOrderDetailsRepo.getOrderDetails(userId, orderId);
    }

}
