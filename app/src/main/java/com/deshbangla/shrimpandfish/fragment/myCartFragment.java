package com.deshbangla.shrimpandfish.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deshbangla.shrimpandfish.adapter.CartItemAdapter;
import com.deshbangla.shrimpandfish.model.CartItem;
import com.deshbangla.shrimpandfish.model.User;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.utils.Utils;
import com.deshbangla.shrimpandfish.activities.CheckoutActivity;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class myCartFragment extends Fragment implements CartItemAdapter.RefreshCartItems{

    private CartItemAdapter cartItemAdapter;
    private RecyclerView cartItemsRecycler;
    private LinearLayout empty;
    private List<CartItem> cartItems = new ArrayList<>();
    private User user;
    private CartItemViewModel cartItemViewModel;
    private AppCompatButton loginToView, checkout;
    private CardView cardView;
    private TextView total, subTotal;
    private int i;

    public myCartFragment(int i) {
        this.i = i;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemViewModel = ViewModelProviders.of(this).get(CartItemViewModel.class);

        empty = view.findViewById(R.id.empty);
        loginToView = view.findViewById(R.id.loginToView);
        cardView = view.findViewById(R.id.cardView);
        total = view.findViewById(R.id.total);
        subTotal = view.findViewById(R.id.subTotal);
        checkout = view.findViewById(R.id.checkout);
        cartItemsRecycler = view.findViewById(R.id.cartItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        cartItemsRecycler.setLayoutManager(linearLayoutManager);
        cartItemAdapter = new CartItemAdapter(getContext(), cartItems, myCartFragment.this);
        cartItemsRecycler.setAdapter(cartItemAdapter);

        loginToView.setOnClickListener(v -> {
            if (i==1){
                if (getActivity()!=null){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_container, new LoginFragment(1, "main"))
                            .commit();
                    MainActivity.adapter.setSelected(4);
                    MainActivity.navBar.show(4,true);
                }
            } else if (i==0){
                if (getActivity()!=null){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_cart_wishlist, new LoginFragment(0, "cart"))
                            .commit();
                }
            }
        });

        if (getContext()!=null){
            if (SharedPreferenceData.getUserPreferenceData(getContext()) != null){
                user = SharedPreferenceData.getUserPreferenceData(getContext());
                getCartItems();
                loginToView.setVisibility(View.GONE);
                cartItemsRecycler.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
            }else {
                loginToView.setVisibility(View.VISIBLE);
                cartItemsRecycler.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
            }
        }

        checkout.setOnClickListener(v -> {

            if (cartItemAdapter.getItemCount()!=0){
                if(i==1){
                    if (getActivity()!=null){
                        getActivity().finish();
                    }
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    intent.putExtra("activity","main");
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), CheckoutActivity.class));
                }

            }else {
                Utils.showToastLong(getContext(), "No items in cart");
            }


        });

        return view;
    }

    private void getCartItems() {
        cartItemViewModel.getCartItems(user.getId()).observe(getViewLifecycleOwner(), cartItemResponse -> {
            if (cartItemResponse!=null){
                cartItems.addAll(cartItemResponse.getCartItem());
                cartItemAdapter.notifyDataSetChanged();
                if (cartItemAdapter.getItemCount() != 0){
                    MainActivity.navBar.setCount(2, String.valueOf(cartItemAdapter.getItemCount()));
                    empty.setVisibility(View.GONE);
                    cartItemsRecycler.setVisibility(View.VISIBLE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                    cartItemsRecycler.setVisibility(View.GONE);
                }
                total.setText(String.valueOf(cartItemResponse.getTotal()).concat(getResources().getString(R.string.price)));
                subTotal.setText(String.valueOf(cartItemResponse.getTotal()).concat((getResources().getString(R.string.price))));
            }
        });
    }

    @Override
    public void refresh() {
        cartItemViewModel.getCartItems(user.getId()).observe(getViewLifecycleOwner(), cartItemResponse -> {
            if (cartItemResponse!=null){
                cartItems.clear();
                cartItems.addAll(cartItemResponse.getCartItem());
                cartItemAdapter.notifyDataSetChanged();
                total.setText(String.valueOf(cartItemResponse.getTotal()).concat(getResources().getString(R.string.price)));
                subTotal.setText(String.valueOf(cartItemResponse.getTotal()).concat((getResources().getString(R.string.price))));
                if (cartItemAdapter.getItemCount()!=0){
                    MainActivity.navBar.setCount(2, String.valueOf(cartItemAdapter.getItemCount()));
                } else {
                    MainActivity.navBar.clearCount(2);
                    empty.setVisibility(View.VISIBLE);
                    cartItemsRecycler.setVisibility(View.GONE);
                }
            }
        });
    }
}