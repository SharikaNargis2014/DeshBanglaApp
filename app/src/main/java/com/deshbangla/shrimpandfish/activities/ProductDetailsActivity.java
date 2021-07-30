package com.deshbangla.shrimpandfish.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.adapter.RelatedItemsAdapter;
import com.deshbangla.shrimpandfish.model.productDetails.Products;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.CheckDoubleItem;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.viewmodel.CartItemPostViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;
import com.deshbangla.shrimpandfish.viewmodel.ProductDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity{

    private Fragment fragment = null;
    private Fragment searchFragment = null;

    private ImageView productImage;
    private TextView productName, productPrice, proDes;
    private RecyclerView relatedItemsRecycler;
    private RelatedItemsAdapter relatedItemsAdapter;
    private CartItemViewModel cartItemViewModel;
    private CartItemPostViewModel cartItemPostViewModel;

    private AppCompatButton addToCart, buyNow;

    private List<Products> relatedItemList = new ArrayList<>();

    private ProductDetailsViewModel productDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productDetailsViewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel.class);
        cartItemViewModel = ViewModelProviders.of(this).get(CartItemViewModel.class);
        cartItemPostViewModel = ViewModelProviders.of(this).get(CartItemPostViewModel.class);

        Utils.hideStatusBar(getWindow(),false);

        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        proDes = findViewById(R.id.proDes);
        relatedItemsRecycler = findViewById(R.id.relatedItemsRecycler);
        addToCart = findViewById(R.id.addToCart);
        buyNow = findViewById(R.id.buyNow);
        ImageButton backBTN= findViewById(R.id.backBTN);
        ImageButton cart= findViewById(R.id.cart);
        ImageButton wishlist= findViewById(R.id.wishListIB);

        cart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailsActivity.this, CartWishlistActivity.class);
            intent.putExtra("activity", 0);
            intent.putExtra("product_id", getIntent().getIntExtra("id", 0));
            intent.putExtra("page", "cart");
            startActivity(intent);
            finish();
        });

        wishlist.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailsActivity.this, CartWishlistActivity.class);
            intent.putExtra("activity", 0);
            intent.putExtra("product_id", getIntent().getIntExtra("id", 0));
            intent.putExtra("page", "wishlist");
            startActivity(intent);
            finish();
        });

        backBTN.setOnClickListener(v -> onBackPressed());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        relatedItemsRecycler.setLayoutManager(gridLayoutManager);

        relatedItemsAdapter = new RelatedItemsAdapter(this, relatedItemList);
        relatedItemsRecycler.setAdapter(relatedItemsAdapter);

        getProductDetails();

    }

    private void getProductDetails() {
        productDetailsViewModel.getProductDetails(getIntent().getIntExtra("id",0)).observe(this, ProductDetailsResponse->{
            if (ProductDetailsResponse != null){
                Glide.with(this).load(getString(R.string.ImgUrl).concat(ProductDetailsResponse.getProducts().getImage())).into(productImage);
                productName.setText(ProductDetailsResponse.getProducts().getName());
                productPrice.setText(ProductDetailsResponse.getProducts().getSellPrice().concat(getResources().getString(R.string.price))+ " (" + ProductDetailsResponse.getProducts().getWeight() + " KG" + ")");
                proDes.setText(ProductDetailsResponse.getProducts().getDesp());
                relatedItemList.addAll(ProductDetailsResponse.getRelatedFish());
                relatedItemsAdapter.notifyDataSetChanged();

                addToCart.setOnClickListener(v -> {
                    if (SharedPreferenceData.getUserPreferenceData(this)!=null){
                        checkItemPostCart(ProductDetailsResponse.getProducts().getId(), ProductDetailsResponse.getProducts().getImage(), ProductDetailsResponse.getProducts().getName(), ProductDetailsResponse.getProducts().getSellPrice());
                    } else {
                        Utils.showToastLong(this, "Login to add items");
                    }

                });

                buyNow.setOnClickListener(v -> {
                    if (SharedPreferenceData.getUserPreferenceData(this)!=null){
                        checkItemPostCartBuy(ProductDetailsResponse.getProducts().getId(), ProductDetailsResponse.getProducts().getImage(), ProductDetailsResponse.getProducts().getName(), ProductDetailsResponse.getProducts().getSellPrice());
                    } else {
                        Utils.showToastLong(this, "Login to buy items");
                    }
                });

            }
        });
    }

    private void checkItemPostCartBuy(Integer id, String image, String name, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(this).getId()).observe(this, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    startActivity(new Intent(ProductDetailsActivity.this, CheckoutActivity.class));
                } else{
                    postCartItemBuy(id, image, name, sellPrice);
                }
            }
        });
    }

    private void postCartItemBuy(Integer id, String image, String name, String sellPrice) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(this).getId(), image, name, sellPrice, "1").observe(this, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                startActivity(new Intent(ProductDetailsActivity.this, CheckoutActivity.class));
            }else {
                Utils.showToastLong(this, "Failed.");
            }
        });
    }

    private void checkItemPostCart(Integer id, String image, String name, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(this).getId()).observe(this, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    Utils.showToastShort(this, "Item already added.");
                } else{
                    postCartItem(id, image, name, sellPrice);
                }
            }
        });
    }


    private void postCartItem(Integer id, String image, String name, String sellPrice) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(this).getId(), image, name, sellPrice, "1").observe(this, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                Utils.showToastShort(this, "Item added to cart.");
            }else {
                Utils.showToastLong(this, "Failed.");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}