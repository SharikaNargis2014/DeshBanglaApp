package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deshbangla.shrimpandfish.model.IndividualOderDetails;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.viewmodel.ProductDetailsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderViewHolder> {

    private Context context;
    private List<IndividualOderDetails> orderItems;

    private ProductDetailsViewModel productDetailsViewModel;

    public OrderItemsAdapter(Context context, List<IndividualOderDetails> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @NotNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_order_items, parent, false);
        return new OrderItemsAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrderViewHolder holder, int position) {
        productDetailsViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductDetailsViewModel.class);
        setImage(orderItems.get(position).getProductId(), holder.itemImageIV);
        holder.itemNameTV.setText(orderItems.get(position).getProductName());
        holder.itemPriceTV.setText(String.valueOf(orderItems.get(position).getProductPrice()));
        holder.itemQuantityTV.setText(String.valueOf(orderItems.get(position).getProductQuantity()));
        holder.subTotalTV.setText(String.valueOf(orderItems.get(position).getOrderTotal()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.showToastShort(context, String.valueOf(orderItems.get(position).getProductId()));
            }
        });

    }

    private void setImage(String productId, ImageView itemImageIV) {
        productDetailsViewModel.getProductDetails(Integer.parseInt(productId)).observe((LifecycleOwner) context, productDetailsResponse -> {
            if (productDetailsResponse!=null){
                Glide.with(context).load(context.getResources().getString(R.string.ImgUrl).concat(productDetailsResponse.getProducts().getImage())).into(itemImageIV);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        ImageView itemImageIV;
        TextView itemNameTV, itemPriceTV, itemQuantityTV, subTotalTV;

        public OrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemImageIV = itemView.findViewById(R.id.itemImageIV);
            itemNameTV = itemView.findViewById(R.id.itemNameTV);
            itemPriceTV = itemView.findViewById(R.id.itemPriceTV);
            itemQuantityTV = itemView.findViewById(R.id.itemQuantityTV);
            subTotalTV = itemView.findViewById(R.id.subTotalTV);

        }
    }
}
