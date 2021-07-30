package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.WishlistDeleteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistDeleteRepo {

    private ApiService apiService;

    public WishlistDeleteRepo(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<WishlistDeleteResponse> deleteWishlistItem(int id){
        MutableLiveData<WishlistDeleteResponse> data = new MutableLiveData<>();
        apiService.deleteWishlistItem(id).enqueue(new Callback<WishlistDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<WishlistDeleteResponse> call, @NonNull Response<WishlistDeleteResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WishlistDeleteResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
