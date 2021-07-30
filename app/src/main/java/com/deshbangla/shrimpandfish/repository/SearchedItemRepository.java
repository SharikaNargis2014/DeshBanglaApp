package com.deshbangla.shrimpandfish.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchedItemRepository {

    private ApiService apiService;

    public SearchedItemRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<SearchResponse> getSearchedItem(String searchedItem, int page){
        MutableLiveData<SearchResponse> data = new MutableLiveData<>();
        apiService.getSearchedItem(searchedItem, page).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;

    }

}
