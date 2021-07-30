package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.WishListItem;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.CheckDoubleItem;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.CheckoutActivity;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.activities.ProductDetailsActivity;
import com.deshbangla.shrimpandfish.viewmodel.CartItemPostViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;
import com.deshbangla.shrimpandfish.viewmodel.WishlistDeleteViewModel;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private Context context;
    private List<WishListItem> wishList;
    private int flag;
    private RefreshWishlistItems refreshWishlistItems;
    private WishlistDeleteViewModel wishlistDeleteViewModel;
    private CartItemViewModel cartItemViewModel;
    private CartItemPostViewModel cartItemPostViewModel;

    public interface RefreshWishlistItems{
        void refresh();
    }

    public WishListAdapter(Context context, List<WishListItem> wishList, int flag, RefreshWishlistItems refreshWishlistItems) {
        this.context = context;
        this.wishList = wishList;
        this.flag = flag;
        this.refreshWishlistItems = refreshWishlistItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        wishlistDeleteViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(WishlistDeleteViewModel.class);
        cartItemViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CartItemViewModel.class);
        cartItemPostViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(CartItemPostViewModel.class);

        Glide.with(context).
                load(context.
                        getResources().getString(R.string.ImgUrl).concat(wishList.get(position).getImage())).
                into(holder.itemImage);

        holder.itemName.setText(wishList.get(position).getName());
        holder.itemPrice.setText(wishList.get(position).getSellPrice().concat(context.getResources().getString(R.string.price)));

        holder.delete.setOnClickListener(v -> deleteWishlistItem(wishList.get(position).getId(), position));

        holder.buyNowWish.setOnClickListener(v -> {
            checkItemPostCartBuy(wishList.get(position).getProductId(), wishList.get(position).getImage(), wishList.get(position).getName(), wishList.get(position).getSellPrice());
        });

        holder.addToCartWish.setOnClickListener(v -> {
            checkItemPostCart(wishList.get(position).getProductId(), wishList.get(position).getImage(), wishList.get(position).getName(), wishList.get(position).getSellPrice());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("id", wishList.get(position).getProductId());
            context.startActivity(intent);
        });

    }

    private void checkItemPostCartBuy(Integer id, String image, String name, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    if (flag == 1){
                        Intent intent = new Intent(context, CheckoutActivity.class);
                        intent.putExtra("activity","main");
                        context.startActivity(intent);
                    } else {
                        context.startActivity(new Intent(context, CheckoutActivity.class));
                    }
                } else{
                    postCartItemBuy(id, image, name, sellPrice);
                }
            }
        });
    }

    private void postCartItemBuy(Integer id, String image, String name, String sellPrice) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(context).getId(), image, name, sellPrice, "1").observe((LifecycleOwner) context, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                if (flag == 1){
                    Intent intent = new Intent(context, CheckoutActivity.class);
                    intent.putExtra("activity","main");
                    context.startActivity(intent);
                } else {
                    context.startActivity(new Intent(context, CheckoutActivity.class));
                }
            }else {
                Utils.showToastLong(context, "Failed.");
            }
        });
    }

    private void checkItemPostCart(Integer id, String image, String name, String sellPrice) {
        cartItemViewModel.getCartItems(SharedPreferenceData.getUserPreferenceData(context).getId()).observe((LifecycleOwner) context, cartItemResponse -> {
            if (cartItemResponse!=null){
                if (CheckDoubleItem.checkCartItems(id, cartItemResponse.getCartItem())){
                    Utils.showToastShort(context, "Item already added.");
                } else{
                    postCartItem(id, image, name, sellPrice, cartItemResponse.getCartItem().size());
                }
            }
        });
    }


    private void postCartItem(Integer id, String image, String name, String sellPrice, int cartSize) {
        cartItemPostViewModel.postCartItem(id, SharedPreferenceData.getUserPreferenceData(context).getId(), image, name, sellPrice, "1").observe((LifecycleOwner) context, cartItemPostResponse -> {
            if (cartItemPostResponse!=null){
                Utils.showToastShort(context, "Item added to cart.");
                MainActivity.navBar.setCount(2, String.valueOf(cartSize+1));
            }else {
                Utils.showToastLong(context, "Failed.");
            }
        });
    }

    private void deleteWishlistItem(Integer id, int position) {
        wishlistDeleteViewModel.deleteWishlistItem(id).observe(((LifecycleOwner) context), wishlistDeleteResponse -> {
            if (wishlistDeleteResponse != null){
                Utils.showToastShort(context, wishlistDeleteResponse.getWishlist());
                wishList.remove(position);
                notifyDataSetChanged();
                refreshWishlistItems.refresh();
            }else {
                Utils.showToastShort(context, "Failed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView itemName, itemPrice;
        ImageButton delete;
        AppCompatButton addToCartWish, buyNowWish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            delete = itemView.findViewById(R.id.delete);
            addToCartWish = itemView.findViewById(R.id.addToCartWish);
            buyNowWish = itemView.findViewById(R.id.buyNowWish);

        }
    }
}
