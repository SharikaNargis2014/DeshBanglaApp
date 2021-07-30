package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.WishListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishListRepository {

    private ApiService apiService;

    public WishListRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<WishListResponse> getWishList(int id){
        MutableLiveData<WishListResponse> data = new MutableLiveData<>();
        apiService.getWishList(id).enqueue(new Callback<WishListResponse>() {
            @Override
            public void onResponse(@NonNull Call<WishListResponse> call,@NonNull Response<WishListResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WishListResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
