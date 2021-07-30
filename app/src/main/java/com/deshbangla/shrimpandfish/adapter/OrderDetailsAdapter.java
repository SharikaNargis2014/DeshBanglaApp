package com.deshbangla.shrimpandfish.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deshbangla.shrimpandfish.model.OrderDetails;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.activities.OrderDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private Context context;
    private List<OrderDetails> orderDetails;

    public OrderDetailsAdapter(Context context, List<OrderDetails> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        String total = orderDetails.get(position).getOrderTotal().toString().concat(context.getResources().getString(R.string.price));
        holder.orderId.setText(context.getResources().getString(R.string.orderId).concat(orderDetails.get(position).getPin()));
        holder.orderDate.setText(context.getResources().getString(R.string.placedOn).concat(orderDetails.get(position).getCreatedAt()));
        holder.customerName.setText(orderDetails.get(position).getCustomerName());
        holder.customerAddress.setText(orderDetails.get(position).getAddress());
        holder.subTotal.setText(total);

        holder.itemView.setOnClickListener(v -> {

//            Utils.showToastShort(context, orderDetails.get(position).getId().toString());
            Intent intent = new Intent(((Activity)context),OrderDetailsActivity.class);
            intent.putExtra("order_id", orderDetails.get(position).getId());
            ((Activity)context).startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId, orderDate, subTotal, customerName, customerAddress;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.orderId);
            orderDate = itemView.findViewById(R.id.orderDate);
            subTotal = itemView.findViewById(R.id.subTotal);
            customerName = itemView.findViewById(R.id.customerName);
            customerAddress = itemView.findViewById(R.id.customerAddress);

        }
    }
}
