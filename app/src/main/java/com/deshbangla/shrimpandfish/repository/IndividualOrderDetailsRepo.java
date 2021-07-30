package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.IndividualOrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualOrderDetailsRepo {

    private ApiService apiService;

    public IndividualOrderDetailsRepo(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<IndividualOrderDetailsResponse> getOrderDetails(int user_id, int orderId){
        MutableLiveData<IndividualOrderDetailsResponse> data = new MutableLiveData<>();
        apiService.getOrderDetails(user_id, orderId).enqueue(new Callback<IndividualOrderDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<IndividualOrderDetailsResponse> call, @NonNull Response<IndividualOrderDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<IndividualOrderDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
