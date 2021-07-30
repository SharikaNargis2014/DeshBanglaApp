package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.WishListPostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListPostRepository {

    private ApiService apiService;

    public WishListPostRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<WishListPostResponse> postWish(int id,int userId, int product_id){
        MutableLiveData<WishListPostResponse> data = new MutableLiveData<>();
        apiService.postWish(id, userId, product_id).enqueue(new Callback<WishListPostResponse>() {
            @Override
            public void onResponse(Call<WishListPostResponse> call, Response<WishListPostResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WishListPostResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
