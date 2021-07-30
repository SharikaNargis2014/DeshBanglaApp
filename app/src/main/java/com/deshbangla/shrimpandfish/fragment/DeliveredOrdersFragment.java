package com.deshbangla.shrimpandfish.fragment;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.deshbangla.shrimpandfish.adapter.OrderDetailsAdapter;
import com.deshbangla.shrimpandfish.model.OrderDetails;
import com.deshbangla.shrimpandfish.model.User;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.viewmodel.DeliveredOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveredOrdersFragment extends Fragment {

    private RecyclerView deliveredOrdersRecycler;
    private NestedScrollView nested;
    private LinearLayout empty;
    private OrderDetailsAdapter orderDetailsAdapter;
    private List<OrderDetails> deliveredOrders =  new ArrayList<>();
    private DeliveredOrdersViewModel deliveredOrdersViewModel;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivered_orders, container, false);
        deliveredOrdersViewModel = ViewModelProviders.of(this).get(DeliveredOrdersViewModel.class);

        deliveredOrdersRecycler = view.findViewById(R.id.deliveredOrders);
        nested = view.findViewById(R.id.nestedScroll);
        empty = view.findViewById(R.id.empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        deliveredOrdersRecycler.setLayoutManager(layoutManager);

        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), deliveredOrders);

        deliveredOrdersRecycler.setAdapter(orderDetailsAdapter);

        if (getContext()!=null){
            if (SharedPreferenceData.getUserPreferenceData(getContext()) !=null){
                user = SharedPreferenceData.getUserPreferenceData(getContext());
                getDeliveredOrders();
            }
        }



        return view;
    }

    private void getDeliveredOrders() {
        deliveredOrdersViewModel.getDeliveredOrders(user.getId()).observe(getViewLifecycleOwner(), orderDetailsResponse -> {
            if (orderDetailsResponse!= null){
                deliveredOrders.addAll(orderDetailsResponse.getOrders());
                orderDetailsAdapter.notifyDataSetChanged();
                if (orderDetailsAdapter.getItemCount() == 0){
                    nested.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                } else {
                    nested.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });
    }
}