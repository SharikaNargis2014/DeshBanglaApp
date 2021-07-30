package com.deshbangla.shrimpandfish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.viewmodel.ShippingViewModel;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

public class CheckoutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppCompatEditText userName, userEmail, userContact, userAddress, userMessage;
    private AppCompatButton orderBtn;
    private ShippingViewModel shippingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleMarginStart(0);
        toolbar.setTitle("Checkout");
        toolbar.setTitleTextColor(getResources().getColor(R.color.PrimaryColor));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        shippingViewModel = ViewModelProviders.of(CheckoutActivity.this).get(ShippingViewModel.class);

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userContact = findViewById(R.id.userContact);
        userAddress = findViewById(R.id.userAddress);
        userMessage = findViewById(R.id.userMessage);
        orderBtn = findViewById(R.id.orderBtn);

        orderBtn.setOnClickListener(v -> checkInfoValidity());


    }

    private void checkInfoValidity() {
        if (Utils.textValidCheck(userName.getText().toString().trim())) {
            if (Utils.emailValidationCheck(userEmail.getText().toString().trim())) {
                if (Utils.isValidMobile(userContact.getText().toString().trim())) {
                    if (Utils.textValidCheck(userAddress.getText().toString().trim())) {
                        if (userAddress.getText().toString().trim().length() >= 10){
                            if (Utils.textValidCheck(userMessage.getText().toString().trim())){
                                postShippingInfo(userName.getText().toString().trim(),
                                        userEmail.getText().toString().trim(),
                                        userContact.getText().toString().trim(),
                                        userAddress.getText().toString().trim(),
                                        userMessage.getText().toString().trim());
                            } else {
                                userMessage.setError("Enter a Valid message.");
                            }

                        } else {
                            userAddress.setError("Enter a Detailed Address.");
                        }
                    } else {
                        userAddress.setError("Enter a Detailed Address.");
                    }
                } else {
                    userContact.setError("Enter a Valid Phone number.");
                }
            } else {
                userEmail.setError("Enter a Valid Email.");
            }
        } else {
            userName.setError("Enter a Valid Name.");
        }
    }

    private void postShippingInfo(String userName, String userEmail, String userContact, String userAddress, String userMessage) {

        shippingViewModel.postShippingInfo(SharedPreferenceData.getUserPreferenceData(CheckoutActivity.this).getId(), userName, userEmail, userContact, userAddress, userMessage, SharedPreferenceData.getUserPreferenceData(CheckoutActivity.this).getId()).observe(this, shippingResponse -> {
            if (shippingResponse != null){
                Utils.showToastLong(CheckoutActivity.this, "Order Placed Successfully");
                finish();
                if (getIntent().getStringExtra("activity")!=null){
                    if (getIntent().getStringExtra("activity").equals("main")){
                        startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                    }
                }
            } else {
                Utils.showToastLong(CheckoutActivity.this, "Failed to place order");
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra("activity")!=null){
            if (getIntent().getStringExtra("activity").equals("main")){
                startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
            }
        } else {
            super.onBackPressed();
        }
    }
}