package com.deshbangla.shrimpandfish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.deshbangla.shrimpandfish.fragment.myCartFragment;
import com.deshbangla.shrimpandfish.fragment.wishListFragment;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.databinding.ActivityCartWishlistBinding;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

public class CartWishlistActivity extends AppCompatActivity {

    private ActivityCartWishlistBinding activityCartWishlistBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartWishlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart_wishlist);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        int flag = SYSTEM_UI_FLAG_LAYOUT_STABLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);

        activityCartWishlistBinding.toolbar.setTitleMarginStart(0);
        activityCartWishlistBinding.toolbar.setTitleTextColor(getResources().getColor(R.color.PrimaryColor));
        if (getIntent().getStringExtra("page").equals("cart")){
            activityCartWishlistBinding.toolbar.setTitle("Cart");
        } else if (getIntent().getStringExtra("page").equals("wishlist")){
            activityCartWishlistBinding.toolbar.setTitle("Wishlist");
        }
        setSupportActionBar(activityCartWishlistBinding.toolbar);

        activityCartWishlistBinding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        if (getIntent().getStringExtra("page").equals("cart")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cart_wishlist, new myCartFragment(0)).commit();
        } else if (getIntent().getStringExtra("page").equals("wishlist")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cart_wishlist, new wishListFragment(0)).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("page").equals("cart")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cart_wishlist, new myCartFragment(0)).commit();
        } else if (getIntent().getStringExtra("page").equals("wishlist")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_cart_wishlist, new wishListFragment(0)).commit();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        if (getIntent().getIntExtra("activity",0)==1){
            Intent intent = new Intent(CartWishlistActivity.this, CategoryItemsActivity.class);
            intent.putExtra("type",getIntent().getStringExtra("type"));
            intent.putExtra("title",getIntent().getStringExtra("title"));
            startActivity(intent);
        } else if (getIntent().getIntExtra("activity", 0)==0){
            Intent intent = new Intent(CartWishlistActivity.this, ProductDetailsActivity.class);
            intent.putExtra("id", getIntent().getIntExtra("product_id", 0));
            startActivity(intent);
        }
    }
}