package com.deshbangla.shrimpandfish.fragment;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.utils.SharedPreferenceData;
import com.deshbangla.shrimpandfish.activities.MainActivity;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class myOrdersFragment extends Fragment {

    private TabLayout ordersTabLayout;
    private ViewPager ordersTabViewPager;
    private TabAdapter tabAdapter;
    private AppCompatButton loginToView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        ordersTabLayout = view.findViewById(R.id.ordersTab_layout);
        ordersTabViewPager = view.findViewById(R.id.ordersTabViewPager);
        loginToView = view.findViewById(R.id.loginToView);

        if (getContext()!=null){
            if (SharedPreferenceData.getUserPreferenceData(getContext()) != null){
                ordersTabLayout.setVisibility(View.VISIBLE);
                ordersTabViewPager.setVisibility(View.VISIBLE);
                loginToView.setVisibility(View.GONE);
            } else {
                ordersTabLayout.setVisibility(View.GONE);
                ordersTabViewPager.setVisibility(View.GONE);
                loginToView.setVisibility(View.VISIBLE);
            }
        }

        tabAdapter = new TabAdapter(getChildFragmentManager());
        tabAdapter.addFragment(new PendingOrdersFragment(), "Pending");
        tabAdapter.addFragment(new DeliveredOrdersFragment(), "Delivered");

        ordersTabViewPager.setAdapter(tabAdapter);

        ordersTabLayout.setupWithViewPager(ordersTabViewPager);

        loginToView.setOnClickListener(v -> {
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
        });

        return view;
    }

    private class TabAdapter extends FragmentStatePagerAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        public void addFragment(Fragment fragment, String s){

            fragments.add(fragment);
            strings.add(s);

        }

        public TabAdapter(FragmentManager fm) {
            super(fm);

        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings.get(position);
        }
    }
}