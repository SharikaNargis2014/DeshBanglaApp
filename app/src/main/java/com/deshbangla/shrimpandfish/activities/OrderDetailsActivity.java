package com.deshbangla.shrimpandfish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.deshbangla.shrimpandfish.adapter.OrderItemsAdapter;
import com.deshbangla.shrimpandfish.model.IndividualOderDetails;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.databinding.ActivityOrderDetailsBinding;
import com.deshbangla.shrimpandfish.viewmodel.IndividualOrderDetailsVM;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

public class OrderDetailsActivity extends AppCompatActivity {

    private IndividualOrderDetailsVM individualOrderDetailsVM;
    private ActivityOrderDetailsBinding activityOrderDetailsBinding;
    private List<IndividualOderDetails> orderItems = new ArrayList<>();
    private OrderItemsAdapter orderItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
        individualOrderDetailsVM = new ViewModelProvider(this).get(IndividualOrderDetailsVM.class);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);

        activityOrderDetailsBinding.toolbar.setTitleMarginStart(0);
        activityOrderDetailsBinding.toolbar.setTitle("Order Details");
        activityOrderDetailsBinding.toolbar.setTitleTextColor(getResources().getColor(R.color.PrimaryColor));
        setSupportActionBar(activityOrderDetailsBinding.toolbar);

        activityOrderDetailsBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        int orderId = getIntent().getIntExtra("order_id", 0);

        if (SharedPreferenceData.getUserPreferenceData(this) != null){
            getOrderDetails(SharedPreferenceData.getUserPreferenceData(this).getId(), orderId);
        }

        orderItemsAdapter = new OrderItemsAdapter(this, orderItems);
        activityOrderDetailsBinding.orderItemRV.setAdapter(orderItemsAdapter);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void getOrderDetails(int userId, int orderID) {
        toggleLoading();
        individualOrderDetailsVM.getOrderDetails(userId, orderID).observe(this, individualOrderDetailsResponse -> {
            toggleLoading();
            orderItems.clear();
            orderItems.addAll(individualOrderDetailsResponse.getOderDetails());
            orderItemsAdapter.notifyDataSetChanged();
            if (individualOrderDetailsResponse!=null){
                activityOrderDetailsBinding.nested.setVisibility(View.VISIBLE);
                if (individualOrderDetailsResponse.getCustomerInfo().getStatus()==0){
                    activityOrderDetailsBinding.orderStatus.setText(getResources().getString(R.string.pending));
                    activityOrderDetailsBinding.orderStatus.setBackground(getResources().getDrawable(R.drawable.pending_bg));
                } else if(individualOrderDetailsResponse.getCustomerInfo().getStatus()==1){
                    activityOrderDetailsBinding.orderStatus.setText(getResources().getString(R.string.delivered));
                    activityOrderDetailsBinding.orderStatus.setBackground(getResources().getDrawable(R.drawable.btn_bg));
                }
                activityOrderDetailsBinding.userName.setText(individualOrderDetailsResponse.getCustomerInfo().getUsername());
                activityOrderDetailsBinding.userContact.setText(String.valueOf(individualOrderDetailsResponse.getCustomerInfo().getPhoneNum()));
                activityOrderDetailsBinding.userNameShip.setText(individualOrderDetailsResponse.getCustomerInfo().getCustomerName());
                activityOrderDetailsBinding.userContactShip.setText(String.valueOf(individualOrderDetailsResponse.getCustomerInfo().getPhoneNum()));
                activityOrderDetailsBinding.userEmailShip.setText(individualOrderDetailsResponse.getCustomerInfo().getEmail());
                activityOrderDetailsBinding.userAddressShip.setText(individualOrderDetailsResponse.getCustomerInfo().getAddress());
                activityOrderDetailsBinding.userMessageShip.setText(individualOrderDetailsResponse.getCustomerInfo().getMessage());
                activityOrderDetailsBinding.subTotalShip.setText(String.valueOf(individualOrderDetailsResponse.getOrderTotal()).concat(getResources().getString(R.string.price)));
            } else {
                activityOrderDetailsBinding.nested.setVisibility(View.GONE);
                Utils.showToastShort(this, "Failed to load data");
            }
        });

    }

    private void toggleLoading(){
        if (activityOrderDetailsBinding.getIsLoading() != null && activityOrderDetailsBinding.getIsLoading()){
            activityOrderDetailsBinding.setIsLoading(false);
        } else {
            activityOrderDetailsBinding.setIsLoading(true);
        }
    }
}