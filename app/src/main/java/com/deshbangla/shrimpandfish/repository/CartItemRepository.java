package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.CartItemResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItemRepository {

    private ApiService apiService;

    public CartItemRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<CartItemResponse> getCartItems(int id){
        MutableLiveData<CartItemResponse> data = new MutableLiveData<>();
        apiService.getCartItems(id).enqueue(new Callback<CartItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartItemResponse> call, @NonNull Response<CartItemResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CartItemResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
