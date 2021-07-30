package com.deshbangla.shrimpandfish.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.deshbangla.shrimpandfish.adapter.WishListAdapter;
import com.deshbangla.shrimpandfish.model.User;
import com.deshbangla.shrimpandfish.model.WishListItem;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.deshbangla.shrimpandfish.viewmodel.WishListViewModel;

import java.util.ArrayList;
import java.util.List;


public class wishListFragment extends Fragment implements WishListAdapter.RefreshWishlistItems{

    private RecyclerView wishListRecycler;
    private WishListAdapter wishListAdapter;
    private WishListViewModel wishListViewModel;
    private LinearLayout empty;
    private List<WishListItem> wishList = new ArrayList<>();
    private User user;
    private AppCompatButton loginToView;
    private NestedScrollView nestedScroll;
    private int i;

    public wishListFragment(int i) {
        this.i = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        wishListViewModel = ViewModelProviders.of(this).get(WishListViewModel.class);

        nestedScroll = view.findViewById(R.id.nestedScroll);
        empty = view.findViewById(R.id.empty);
        loginToView = view.findViewById(R.id.loginToView);
        wishListRecycler = view.findViewById(R.id.wishList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        wishListRecycler.setLayoutManager(linearLayoutManager);
        if (i==1){
            wishListAdapter = new WishListAdapter(getContext(), wishList, 1, this);
        } else if (i==0){
            wishListAdapter = new WishListAdapter(getContext(), wishList, 0, this);
        }

        wishListRecycler.setAdapter(wishListAdapter);

        if (getContext() != null) {
            if (SharedPreferenceData.getUserPreferenceData(getContext()) != null) {
                wishListRecycler.setVisibility(View.VISIBLE);
                loginToView.setVisibility(View.GONE);
                user = SharedPreferenceData.getUserPreferenceData(getContext());
                getWishList();
            } else {
                wishListRecycler.setVisibility(View.GONE);
                loginToView.setVisibility(View.VISIBLE);
            }
        }

        loginToView.setOnClickListener(v -> {
            if (i==1){
                if (getActivity() != null) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_container, new LoginFragment(1, "main"))
                            .commit();
                    MainActivity.adapter.setSelected(4);
                    MainActivity.navBar.show(4, true);
                }
            } else if (i==0){
                if (getActivity()!=null){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_top_to_bottom)
                            .replace(R.id.fragment_cart_wishlist, new LoginFragment(0, "wishList"))
                            .commit();
                }
            }
        });

        return view;
    }

    private void getWishList() {
        wishListViewModel.getWishList(user.getId()).observe(getViewLifecycleOwner(), WishListResponse -> {
            if (WishListResponse != null) {
                wishList.clear();
                wishList.addAll(WishListResponse.getWishlist());
                wishListAdapter.notifyDataSetChanged();
                if (wishListAdapter.getItemCount() != 0) {
                    MainActivity.navBar.setCount(1, String.valueOf(wishListAdapter.getItemCount()));
                    wishListRecycler.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                } else {
                    MainActivity.navBar.clearCount(1);
                    empty.setVisibility(View.VISIBLE);
                    wishListRecycler.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void refresh() {
        getWishList();
    }
}