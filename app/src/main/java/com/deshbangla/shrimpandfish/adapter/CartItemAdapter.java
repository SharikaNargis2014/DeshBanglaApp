package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.CartItem;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.ProductDetailsActivity;
import com.deshbangla.shrimpandfish.viewmodel.CartItemDeleteViewModel;
import com.deshbangla.shrimpandfish.viewmodel.UpdateQtyViewModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private RefreshCartItems refreshCartItems;
    private UpdateQtyViewModel updateQtyViewModel;
    private CartItemDeleteViewModel cartItemDeleteViewModel;

    public interface RefreshCartItems{
        void refresh();
    }

    public CartItemAdapter(Context context, List<CartItem> cartItems, RefreshCartItems refreshCartItems) {
        this.context = context;
        this.cartItems = cartItems;
        this.refreshCartItems = refreshCartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        updateQtyViewModel = ViewModelProviders.of((FragmentActivity) context).get(UpdateQtyViewModel.class);
        cartItemDeleteViewModel = ViewModelProviders.of((FragmentActivity) context).get(CartItemDeleteViewModel.class);
        Glide.with(context).
                load(context.getResources().getString(R.string.ImgUrl).concat(cartItems.get(position).getImage())).
                into(holder.itemImage);
        holder.itemName.setText(cartItems.get(position).getName());
        holder.itemPrice.setText(cartItems.get(position).getSubtotal().concat(context.getResources().getString(R.string.price)));
        holder.itemQuantity.setText(cartItems.get(position).getQty());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("id", cartItems.get(position).getProductId());
            context.startActivity(intent);
        });

        holder.add.setOnClickListener(v -> {

            int qty = Integer.parseInt(holder.itemQuantity.getText().toString());
            qty++;
            updateQty(cartItems.get(position).getProductId(), SharedPreferenceData.getUserPreferenceData(context).getId(), String.valueOf(qty), holder.itemPrice, holder.itemQuantity);

        });

        holder.remove.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.itemQuantity.getText().toString());
            if (qty == 1){
                Utils.showToastLong(context, "Items can't be 0");
            }
            if (qty>1){
                qty--;
            }
            updateQty(cartItems.get(position).getProductId(), SharedPreferenceData.getUserPreferenceData(context).getId(), String.valueOf(qty), holder.itemPrice, holder.itemQuantity);

        });

        holder.delete.setOnClickListener(v -> {
            deleteCartItem(cartItems.get(position).getId(), position);
        });

    }

    private void deleteCartItem(int id, int position) {
        cartItemDeleteViewModel.deleteCartItem(id).observe((LifecycleOwner) context, cartItemDeleteResponse -> {
            if (cartItemDeleteResponse != null){
                cartItems.remove(position);
                notifyDataSetChanged();
                refreshCartItems.refresh();

            } else{
                Utils.showToastLong(context, "Failed");
            }
        });
    }

    private void updateQty(Integer id, Integer user_id, String qty, TextView itemPrice, TextView itemQuantity) {
        updateQtyViewModel.updateQty(id, user_id, qty).observe((LifecycleOwner) context, cartItemUpdateResponse -> {
            if (cartItemUpdateResponse != null) {
                itemPrice.setText(String.valueOf(cartItemUpdateResponse.getCart().getSubtotal()).concat(context.getResources().getString(R.string.price)));
                itemQuantity.setText(cartItemUpdateResponse.getCart().getQty());
                refreshCartItems.refresh();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView itemImage;
        ImageButton add, remove, delete;
        TextView itemName, itemPrice, itemQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            delete = itemView.findViewById(R.id.delete);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
        }
    }
}
