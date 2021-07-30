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
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.viewmodel.PendingOrdersViewModel;

import java.util.ArrayList;
import java.util.List;

public class PendingOrdersFragment extends Fragment {

    private RecyclerView pendingOrdersRecycler;
    private OrderDetailsAdapter orderDetailsAdapter;
    private NestedScrollView nested;
    private LinearLayout empty;
    private List<OrderDetails> pendingOrders = new ArrayList<>();
    private PendingOrdersViewModel pendingOrdersViewModel;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_orders, container, false);
        pendingOrdersViewModel = ViewModelProviders.of(this).get(PendingOrdersViewModel.class);

        pendingOrdersRecycler = view.findViewById(R.id.pendingOrders);
        nested = view.findViewById(R.id.nestedScroll);
        empty = view.findViewById(R.id.empty);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, true);
        pendingOrdersRecycler.setLayoutManager(layoutManager);

        orderDetailsAdapter = new OrderDetailsAdapter(getContext(), pendingOrders);

        pendingOrdersRecycler.setAdapter(orderDetailsAdapter);

        if (getContext()!=null){
            if (SharedPreferenceData.getUserPreferenceData(getContext()) !=null){
                user = SharedPreferenceData.getUserPreferenceData(getContext());
                getPendingOrders();
            }
        }

        return view;
    }

    private void getPendingOrders() {
        pendingOrdersViewModel.getPendingOrders(user.getId()).observe(getViewLifecycleOwner(), orderDetailsResponse -> {
            if (orderDetailsResponse!=null){
                pendingOrders.addAll(orderDetailsResponse.getOrders());
                orderDetailsAdapter.notifyDataSetChanged();
                if (orderDetailsAdapter.getItemCount() == 0){
                    nested.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                } else {
                    nested.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                    MainActivity.navBar.setCount(3, String.valueOf(orderDetailsAdapter.getItemCount()));
                }
            }
        });
    }
}