package com.deshbangla.shrimpandfish.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.deshbangla.shrimpandfish.network.ApiClient;
import com.deshbangla.shrimpandfish.network.ApiService;
import com.deshbangla.shrimpandfish.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignUpRepository {

    private ApiService apiService;

    public UserSignUpRepository(){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<SignUpResponse> signUpUser(String name, String email, String password, String passCon){
        MutableLiveData<SignUpResponse> data = new MutableLiveData<>();
        apiService.signUpUser(name, email, password, passCon).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
