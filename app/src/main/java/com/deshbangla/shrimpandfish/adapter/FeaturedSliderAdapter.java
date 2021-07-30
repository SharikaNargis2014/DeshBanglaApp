package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.banner.Banner;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.CheckDoubleItem;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.CheckoutActivity;
import com.deshbangla.shrimpandfish.activities.ProductDetailsActivity;
import com.deshbangla.shrimpandfish.viewmodel.CartItemPostViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;
import com.deshbangla.shrimpandfish.viewmodel.ProductDetailsViewModel;

import java.util.List;

public class FeaturedSliderAdapter extends PagerAdapter {

    Context context;
    List<Banner> bannerList;
    private CartItemViewModel cartItemViewModel;
    private CartItemPostViewModel cartItemPostViewModel;
    private ProductDetailsViewModel productDetailsViewModel;

    public FeaturedSliderAdapter(Context context, List<Banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.featured_item, container, false);

        cartItemViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartItemViewModel.class);
        cartItemPostViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartItemPostViewModel.class);
        productDetailsViewModel = ViewModelProviders.of((FragmentActivity) context).get(ProductDetailsViewModel.class);

        TextView fishName = view.findViewById(R.id.fishName);
        TextView des = view.findViewById(R.id.des);
        TextView description = view.findViewById(R.id.description);
        ImageView fishImage = view.findViewById(R.id.fishImage);
        AppCompatButton buyNowBtn = view.findViewById(R.id.buyNowBtn);
        fishName.setText(bannerList.get(position).getProductName());
        des.setText(bannerList.get(position).getTitle());
        description.setText(bannerList.get(position).getDesp());
        Glide.with(context).load(context.getString(R.string.ImgUrl).concat(bannerList.get(position).getImage())).into(fishImage);

        buyNowBtn.setOnClickListener(v -> {
            if (SharedPreferenceData.getUserPreferenceData(context) != null){
                getDetails(Integer.parseInt(bannerList.get(position).getProductId()));
            } else {
                Utils.showToastShort(context, "Login to buy items.");
            }
        });

        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("id", Integer.parseInt(bannerList.get(position).getProductId()));
            context.startActivity(intent);
        });

        container.addView(view, 0);
        return view;
    }

    private void getDetails(Integer id) {
        productDetailsViewModel.getProductDetails(id).observe((LifecycleOwner) context, productDetailsResponse -> {
            if (productDetailsResponse!=null){
                checkItemPostCartBuy(productDetailsResponse.getProducts().getId(), productDetailsResponse.getProducts().getImage(), productDetailsResponse.getProducts().getName(), productDetailsResponse.getProducts().getSellPrice());
            } else {
                Utils.showToastLong(context, "Failed.");
            }
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void checkItemPostCartBuy(Integer id, String image, String name, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    Intent intent = new Intent(context, CheckoutActivity.class);
                    intent.putExtra("activity","main");
                    context.startActivity(intent);
                } else{
                    postCartItemBuy(id, image, name, sellPrice);
                }
            }
        });
    }

    private void postCartItemBuy(Integer id, String image, String name, String sellPrice) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(context).getId(), image, name, sellPrice, "1").observe((LifecycleOwner) context, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                Intent intent = new Intent(context, CheckoutActivity.class);
                intent.putExtra("activity","main");
                context.startActivity(intent);
            }else {
                Utils.showToastLong(context, "Failed.");
            }
        });
    }

}
