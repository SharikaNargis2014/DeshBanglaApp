package com.deshbangla.shrimpandfish.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.deshbangla.shrimpandfish.model.User;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.NetworkUtility;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.viewmodel.UserLoginViewModel;


public class LoginFragment extends Fragment {

    private View view;
    private TextView signUp;
    private AppCompatEditText email, pass;
    private Button login;
    private UserLoginViewModel userLoginViewModel;
    private User user;
    private ProgressBar loading;
    int i;
    String flag;

    public LoginFragment(int i, String flag) {
        this.i = i;
        this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        userLoginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel.class);

        signUp = view.findViewById(R.id.signUpPro);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        login = view.findViewById(R.id.login);
        loading = view.findViewById(R.id.loading);

        signUp.setOnClickListener(v -> {
            if (i==1){
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_container, new SignUpFragment(1, "main"))
                            .commit();

                }
            } else if(i == 0){
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_cart_wishlist, new SignUpFragment(0, flag))
                            .commit();

                }
            }

        });

        login.setOnClickListener(v -> {

            String mail = email.getText().toString().trim();
            String password = pass.getText().toString().trim();
            if (getContext() != null){
                if (Utils.textValidCheck(mail)){
                    if (Utils.emailValidationCheck(mail)){
                        if (Utils.textValidCheck(password)){
                            if (password.length()>=8){
                                if (NetworkUtility.isNetworkAvailable(getContext())){
                                    loading.setVisibility(View.VISIBLE);
                                    userLogin(mail, password);
                                } else {
                                    Toast.makeText(getContext(), "No Internet Connection",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                pass.setError("Password must be 8 characters long.");
                            }
                        } else {
                            pass.setError("Required Field.");
                        }
                    } else {
                        email.setError("Invalid Email.");
                    }
                } else {
                    email.setError("Required Field.");
                }
            }

            if (getActivity() != null && getView() != null){
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

        });

        return view;
    }

    private void userLogin(String mail, String password) {
        userLoginViewModel.loginUser(mail, password).observe(getViewLifecycleOwner(), userResponse -> {
            if (userResponse != null){
                user = userResponse.getUser();
                if (getContext() != null){
                    SharedPreferenceData.setUserPreferenceData(getContext(), user);
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
                    switch (flag) {
                        case "main":
                            if (getActivity() != null) {
                                getActivity().finish();
                                getActivity().startActivity(new Intent(getContext(), MainActivity.class));
                            }
                            break;
                        case "cart":
                            if (getActivity() != null) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                                        .replace(R.id.fragment_cart_wishlist, new myCartFragment(0))
                                        .commit();
                            }
                            break;
                        case "wishList":
                            if (getActivity() != null) {
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                                        .replace(R.id.fragment_cart_wishlist, new wishListFragment(0))
                                        .commit();
                            }
                            break;
                    }

                }
            } else {
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Login Failed. Check Your Credentials.", Toast.LENGTH_LONG).show();
            }
        });
    }
}