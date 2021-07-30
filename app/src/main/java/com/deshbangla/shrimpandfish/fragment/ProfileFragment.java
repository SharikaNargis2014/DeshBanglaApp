package com.deshbangla.shrimpandfish.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deshbangla.shrimpandfish.model.User;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.viewmodel.CartItemViewModel;
import com.deshbangla.shrimpandfish.viewmodel.DeliveredOrdersViewModel;
import com.deshbangla.shrimpandfish.viewmodel.PendingOrdersViewModel;
import com.deshbangla.shrimpandfish.viewmodel.WishListViewModel;


public class ProfileFragment extends Fragment {

    private TextView userName, userEmail;
    private User user;
    private CardView logout, pending, delivered, wishlist, cart;

    private AppCompatTextView pendingOrdersCount, deliveredOrdersCount, wishListCount, cartItemsCount;

    private PendingOrdersViewModel pendingOrdersViewModel;
    private DeliveredOrdersViewModel deliveredOrdersViewModel;
    private WishListViewModel wishListViewModel;
    private CartItemViewModel cartItemViewModel;

    private LinearLayout linear;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        pendingOrdersViewModel = new ViewModelProvider(this).get(PendingOrdersViewModel.class);
        deliveredOrdersViewModel = new ViewModelProvider(this).get(DeliveredOrdersViewModel.class);
        wishListViewModel = new ViewModelProvider(this).get(WishListViewModel.class);
        cartItemViewModel = new ViewModelProvider(this).get(CartItemViewModel.class);

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        logout = view.findViewById(R.id.logout);
        progressBar = view.findViewById(R.id.progressBar);
        linear = view.findViewById(R.id.linear23);

        pending = view.findViewById(R.id.pending);
        delivered = view.findViewById(R.id.delivered);
        wishlist = view.findViewById(R.id.wishlist);
        cart = view.findViewById(R.id.cart);

        pendingOrdersCount = view.findViewById(R.id.pendingOrdersCount);
        deliveredOrdersCount = view.findViewById(R.id.deliveredOrdersCount);
        wishListCount = view.findViewById(R.id.wishListCount);
        cartItemsCount = view.findViewById(R.id.cartItemsCount);

        if (SharedPreferenceData.getUserPreferenceData(requireContext()) != null){
            if (getContext() != null){
                user = SharedPreferenceData.getUserPreferenceData(getContext());
                userName.setText(user.getName());
                userEmail.setText(user.getEmail());
            }
            setCount(user.getId());
        }

        pending.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                        .replace(R.id.fragment_container, new myOrdersFragment())
                        .commit();
                MainActivity.adapter.setSelected(3);
                MainActivity.navBar.show(3, true);
            }
        });

        delivered.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                        .replace(R.id.fragment_container, new myOrdersFragment())
                        .commit();
                MainActivity.adapter.setSelected(3);
                MainActivity.navBar.show(3, true);
            }
        });

        wishlist.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                        .replace(R.id.fragment_container, new wishListFragment(1))
                        .commit();
                MainActivity.adapter.setSelected(1);
                MainActivity.navBar.show(1, true);
            }
        });

        cart.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                        .replace(R.id.fragment_container, new myCartFragment(1))
                        .commit();
                MainActivity.adapter.setSelected(2);
                MainActivity.navBar.show(2, true);
            }
        });

        logout.setOnClickListener(v -> {
            if(getContext() != null){
                SharedPreferenceData.logOut(getContext());
                MainActivity.navBar.clearCount(1);
                MainActivity.navBar.clearCount(2);
                MainActivity.navBar.clearCount(3);
                if (getActivity() != null){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_container, new LoginFragment(1, "main"))
                            .commit();
                }
            }


        });


        return view;
    }

    private void setCount(Integer id) {

        progressBar.setVisibility(View.VISIBLE);
        setCountPending(id);
        setCountDelivered(id);
        setCountWishlist(id);
        setCountCart(id);

    }

    private void setCountCart(Integer id) {
        cartItemViewModel.getCartItems(id).observe(getViewLifecycleOwner(), cartItemResponse -> {
            if (cartItemResponse!=null){
                progressBar.setVisibility(View.GONE);
                linear.setVisibility(View.VISIBLE);
                if (cartItemResponse.getCartItem().size()>0){
                    cartItemsCount.setVisibility(View.VISIBLE);
                    cartItemsCount.setText(String.valueOf(cartItemResponse.getCartItem().size()));
                }
            }
        });
    }

    private void setCountWishlist(Integer id) {
        wishListViewModel.getWishList(id).observe(getViewLifecycleOwner(), wishListResponse -> {
            if (wishListResponse!=null){
                if (wishListResponse.getWishlist().size()>0){
                    wishListCount.setVisibility(View.VISIBLE);
                    wishListCount.setText(String.valueOf(wishListResponse.getWishlist().size()));
                }
            }
        });

    }

    private void setCountDelivered(Integer id) {
        deliveredOrdersViewModel.getDeliveredOrders(id).observe(getViewLifecycleOwner(), orderDetailsResponse -> {
            if (orderDetailsResponse!=null){
                if (orderDetailsResponse.getOrders().size()>0){
                    deliveredOrdersCount.setVisibility(View.VISIBLE);
                    deliveredOrdersCount.setText(String.valueOf(orderDetailsResponse.getOrders().size()));
                }
            }
        });
    }

    private void setCountPending(Integer id) {
        pendingOrdersViewModel.getPendingOrders(id).observe(getViewLifecycleOwner(), orderDetailsResponse -> {
            if (orderDetailsResponse!=null){
                if (orderDetailsResponse.getOrders().size()>0){
                    pendingOrdersCount.setVisibility(View.VISIBLE);
                    pendingOrdersCount.setText(String.valueOf(orderDetailsResponse.getOrders().size()));
                }
            }
        });
    }


}