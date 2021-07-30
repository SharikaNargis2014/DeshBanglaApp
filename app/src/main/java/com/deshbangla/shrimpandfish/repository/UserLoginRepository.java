package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginRepository {

    private ApiService apiService;

    public UserLoginRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<UserResponse> loginUser(String email, String password){
        MutableLiveData<UserResponse> data = new MutableLiveData<>();
        apiService.loginUser(email, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
