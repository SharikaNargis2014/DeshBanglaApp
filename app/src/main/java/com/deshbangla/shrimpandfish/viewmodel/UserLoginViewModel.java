package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.UserLoginRepository;
import com.deshbangla.shrimpandfish.response.UserResponse;

public class UserLoginViewModel extends ViewModel {

    private UserLoginRepository userLoginRepository;

    public UserLoginViewModel(){

        userLoginRepository = new UserLoginRepository();

    }

    public LiveData<UserResponse> loginUser(String email, String password){
        return userLoginRepository.loginUser(email, password);
    }
}
