package com.deshbangla.shrimpandfish.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.NetworkUtility;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.viewmodel.UserSignUpViewModel;

public class SignUpFragment extends Fragment {

    private TextView login;
    private AppCompatEditText name, email,phone_number, pass, confirm_pass;
    private UserSignUpViewModel userSignUpViewModel;
    private AppCompatButton signup;
    private ProgressBar loading;
    int i;
    String flag;

    public SignUpFragment(int i, String flag) {
        this.i = i;
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        userSignUpViewModel = ViewModelProviders.of(this).get(UserSignUpViewModel.class);

        loading = view.findViewById(R.id.loading);
        login = view.findViewById(R.id.loginPro);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone_number = view.findViewById(R.id.phone_number);
        pass = view.findViewById(R.id.pass);
        confirm_pass = view.findViewById(R.id.confirm_pass);
        signup = view.findViewById(R.id.signup);

        signup.setOnClickListener(v -> {

            String userName = name.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPassword = pass.getText().toString().trim();
            String userConPass = confirm_pass.getText().toString().trim();

            if (getContext() != null){
                if (Utils.textValidCheck(userName)){
                    if (Utils.textValidCheck(userEmail)){
                        if (Utils.emailValidationCheck(userEmail)){
                            if (userPassword.length() >= 8){
                                if (userConPass.equals(userPassword)){
                                    if (NetworkUtility.isNetworkAvailable(getContext())){
                                        loading.setVisibility(View.VISIBLE);
                                        userSignUp(userName, userEmail, userPassword, userConPass);
                                    } else {
                                        Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
                                    }
                                } else{
                                    confirm_pass.setError("Passwords don't match.");
                                }
                            } else {
                                pass.setError("Password must be 8 characters long.");
                            }
                        } else {
                            email.setError("Enter a valid email address.");
                        }
                    } else {
                        email.setError("Required field.");
                    }
                } else {
                    name.setError("Required field.");
                }
            }

            if (getActivity() != null && getView() != null){
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

        });

        login.setOnClickListener(v -> {
            if (i==1){
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_container, new LoginFragment(1, "main"))
                            .commit();
                }
            }else if (i==0){
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_cart_wishlist, new LoginFragment(0, flag))
                            .commit();
                }
            }

        });

        return view;
    }

    private void userSignUp(String userName, String userEmail, String userPassword, String userConPass) {
        userSignUpViewModel.signUpUser(userName, userEmail, userPassword, userConPass).observe(getViewLifecycleOwner(), signUpResponse -> {
            if (signUpResponse != null){
                Toast.makeText(getContext(), "Successfully Signed up. Please login with your Credentials.", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.GONE);
                if (i == 1){
                    if (getActivity() != null) {
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                                .replace(R.id.fragment_container, new LoginFragment(1, "main"))
                                .commit();
                    }
                } else if (i == 0){
                    if (getActivity() != null) {
                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                                .replace(R.id.fragment_cart_wishlist, new LoginFragment(0, flag))
                                .commit();
                    }
                }
            } else{
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Signing up Failed.", Toast.LENGTH_LONG).show();
            }
        });
    }
}