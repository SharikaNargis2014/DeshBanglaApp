package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.CartItemDeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCartItemRepository {

    private ApiService apiService;

    public DeleteCartItemRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<CartItemDeleteResponse> deleteCartItem(int id){
        MutableLiveData<CartItemDeleteResponse> data = new MutableLiveData<>();
        apiService.deleteCartItem(id).enqueue(new Callback<CartItemDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartItemDeleteResponse> call, @NonNull Response<CartItemDeleteResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CartItemDeleteResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
