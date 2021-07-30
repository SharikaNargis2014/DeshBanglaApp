package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.productDetails.Products;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.CheckDoubleItem;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.activities.ProductDetailsActivity;
import com.deshbangla.shrimpandfish.response.WishListPostResponse;
import com.deshbangla.shrimpandfish.response.WishListResponse;
import com.deshbangla.shrimpandfish.viewmodel.CartItemPostViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;
import com.deshbangla.shrimpandfish.viewmodel.WishListPostViewModel;
import com.deshbangla.shrimpandfish.viewmodel.WishListViewModel;

import java.util.List;

public class RelatedItemsAdapter extends RecyclerView.Adapter<RelatedItemsAdapter.ViewHolder> {

    private WishListPostViewModel wishListPostViewModel;
    private WishListViewModel wishListViewModel;
    private CartItemPostViewModel cartItemPostViewModel;
    private CartItemViewModel cartItemViewModel;
    Context context;
    List<Products> data;

    public RelatedItemsAdapter(Context context, List<Products> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RelatedItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_item, parent, false);
        return new RelatedItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedItemsAdapter.ViewHolder holder, int position) {

        String price = data.get(position).getSellPrice().concat(context.getString(R.string.price)) + " (" + data.get(position).getWeight().concat(context.getString(R.string.weightScale)) + ")";
        wishListPostViewModel = ViewModelProviders.of((FragmentActivity) context).get(WishListPostViewModel.class);
        wishListViewModel = ViewModelProviders.of((FragmentActivity) context).get(WishListViewModel.class);
        cartItemPostViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartItemPostViewModel.class);
        cartItemViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartItemViewModel.class);
        Glide.with(context).load(context.getString(R.string.ImgUrl).concat(data.get(position).getImage())).into(holder.proImg);
        holder.proName.setText(data.get(position).getName());
        holder.proPrice.setText(price);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("id",data.get(position).getId());
            context.startActivity(intent);
        });

        if (SharedPreferenceData.getUserPreferenceData(context)!=null){
            checkItemWishlist(data.get(position).getId(), holder.wishListBtn);
        }

        holder.wishListBtn.setOnClickListener(v -> {
            if (SharedPreferenceData.getUserPreferenceData(context) != null) {
                checkItemPostWishlist(SharedPreferenceData.getUserPreferenceData(context).getId(), position, data.get(position).getId(), holder.wishListBtn);
            } else {
                Utils.showToastLong(context, "Login to add items");
            }
        });

        holder.addToCart.setOnClickListener(v -> {
            if (SharedPreferenceData.getUserPreferenceData(context)!=null){
                checkItemPostCart(data.get(position).getId(), data.get(position).getImage(), data.get(position).getName(), data.get(position).getSellPrice());
            } else {
                Utils.showToastLong(context, "Login to add items");
            }

        });

    }

    private void checkItemPostCart(Integer id, String itemImage, String itemName, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    Utils.showToastShort(context, "Item already added");
                } else{
                    postCartItem(id, itemImage, itemName, sellPrice);
                    MainActivity.navBar.setCount(2, String.valueOf(cartItemResponse.getCartItem().size()+1));
                }
            }
        });
    }

    private void postCartItem(Integer id, String itemImage, String itemName, String sellPrice) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(context).getId(), itemImage, itemName, sellPrice, "1").observe((LifecycleOwner) context, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                Utils.showToastShort(context, "Item added to cart.");
            }else {
                Utils.showToastLong(context, "Failed.");
            }
        });
    }

    private void checkItemPostWishlist(Integer userId, int position, int product_id, ImageButton wishListBtn) {
        wishListViewModel.getWishList(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, new Observer<WishListResponse>() {
            @Override
            public void onChanged(WishListResponse wishListResponse) {
                if (wishListResponse!= null){
                    if (CheckDoubleItem.checkWishlist(product_id, wishListResponse.getWishlist())){
                        Utils.showToastShort(context, "Item already added.");
                    } else {
                        postWish(userId, data.get(position).getId());
                        wishListBtn.setImageResource(R.drawable.ic_fav_filled);
                    }
                }
            }
        });
    }

    private void postWish(Integer userId, int productSlug) {
        wishListPostViewModel.postWish(userId, userId, productSlug).observe((LifecycleOwner) context, new Observer<WishListPostResponse>() {
            @Override
            public void onChanged(WishListPostResponse wishListPostResponse) {
                if (wishListPostResponse != null) {
                    Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkItemWishlist(int product_id, ImageButton wishListBtn) {
        wishListViewModel.getWishList(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, new Observer<WishListResponse>() {
            @Override
            public void onChanged(WishListResponse wishListResponse) {
                if (wishListResponse!= null){
                    if (CheckDoubleItem.checkWishlist(product_id, wishListResponse.getWishlist())){
                        wishListBtn.setImageResource(R.drawable.ic_fav_filled);
                    } else {
                        wishListBtn.setImageResource(R.drawable.ic_fav_border);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView proImg;
        TextView proName;
        TextView proPrice;
        TextView addToCart;
        ImageButton wishListBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proImg = itemView.findViewById(R.id.productImage);
            proName = itemView.findViewById(R.id.productName);
            proPrice = itemView.findViewById(R.id.productPrice);
            addToCart = itemView.findViewById(R.id.addToCart);
            wishListBtn = itemView.findViewById(R.id.wishListBtn);

        }
    }
}