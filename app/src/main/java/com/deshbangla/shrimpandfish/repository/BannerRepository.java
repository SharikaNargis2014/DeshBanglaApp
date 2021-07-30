package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.BannerResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerRepository {

    private ApiService apiService;

    public BannerRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }
    public LiveData<BannerResponse> getBanners(){
        MutableLiveData<BannerResponse> data = new MutableLiveData<>();
        apiService.getBanners().enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(@NonNull Call<BannerResponse> call, @NonNull Response<BannerResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BannerResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
