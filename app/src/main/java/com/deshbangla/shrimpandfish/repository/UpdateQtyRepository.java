package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.CartItemUpdateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateQtyRepository {

    private ApiService apiService;

    public UpdateQtyRepository(){

        apiService = ApiClient.getRetrofit().create(ApiService.class);

    }

    public LiveData<CartItemUpdateResponse> updateQty(int id, int user_id, String qty){
        MutableLiveData<CartItemUpdateResponse> data = new MutableLiveData<>();
        apiService.updateQty(id, user_id, qty).enqueue(new Callback<CartItemUpdateResponse>() {
            @Override
            public void onResponse(Call<CartItemUpdateResponse> call, Response<CartItemUpdateResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CartItemUpdateResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
