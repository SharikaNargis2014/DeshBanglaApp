package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.CartItemPostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItemPostRepository {
    private ApiService apiService;

    public CartItemPostRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<CartItemPostResponse> postCartItem(int id, int user_id, String image, String name, String price, String qty){
        MutableLiveData<CartItemPostResponse> data = new MutableLiveData<>();
        apiService.postToCart(id, user_id, image, name, price, qty).enqueue(new Callback<CartItemPostResponse>() {
            @Override
            public void onResponse(Call<CartItemPostResponse> call, Response<CartItemPostResponse> response) {
                data.setValue(response.body());
            }
            @Override
            public void onFailure(Call<CartItemPostResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
