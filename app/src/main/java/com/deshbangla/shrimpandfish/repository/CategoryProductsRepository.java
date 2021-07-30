package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.ProductResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsRepository {

    private ApiService apiService;

    public CategoryProductsRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<ProductResponse> getCategoryProducts(String slug, int page){
        MutableLiveData<ProductResponse> data = new MutableLiveData<>();
        apiService.getProductsByCategory(slug,page).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

}
