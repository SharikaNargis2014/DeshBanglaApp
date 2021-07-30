package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.UserSignUpRepository;
import com.deshbangla.shrimpandfish.response.SignUpResponse;


public class UserSignUpViewModel extends ViewModel {

    private UserSignUpRepository userSignUpRepository;

    public UserSignUpViewModel(){
        userSignUpRepository = new UserSignUpRepository();
    }

    public LiveData<SignUpResponse> signUpUser(String name, String email, String password, String passCon){

        return userSignUpRepository.signUpUser(name, email, password, passCon);

    }

}
