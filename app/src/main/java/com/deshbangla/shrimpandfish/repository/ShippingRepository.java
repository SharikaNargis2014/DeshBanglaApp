package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.ShippingResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingRepository {

    private ApiService apiService;

    public ShippingRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ShippingResponse> postShippingInfo(int id, String customerName, String email, String phoneNum, String address, String message, int user_id){
        MutableLiveData<ShippingResponse> data = new MutableLiveData<>();
        apiService.postShippingInfo(id, customerName, email, phoneNum, address, message, user_id).enqueue(new Callback<ShippingResponse>() {
            @Override
            public void onResponse(Call<ShippingResponse> call, Response<ShippingResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ShippingResponse> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }
}
