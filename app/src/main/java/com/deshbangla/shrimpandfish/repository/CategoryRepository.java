package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.CategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private ApiService apiService;

    public CategoryRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }
    public LiveData<CategoryResponse> getCategories(){
        MutableLiveData<CategoryResponse> data = new MutableLiveData<>();
        apiService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<CategoryResponse> call,@NonNull Response<CategoryResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CategoryResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
