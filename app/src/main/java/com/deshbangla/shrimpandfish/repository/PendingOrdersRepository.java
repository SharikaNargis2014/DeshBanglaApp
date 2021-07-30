package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.OrderDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrdersRepository {

    private ApiService apiService;

    public PendingOrdersRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<OrderDetailsResponse> getPendingOrders(int id){
        MutableLiveData<OrderDetailsResponse> data = new MutableLiveData<>();
        apiService.getPendingOrders(id).enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailsResponse> call, @NonNull Response<OrderDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
