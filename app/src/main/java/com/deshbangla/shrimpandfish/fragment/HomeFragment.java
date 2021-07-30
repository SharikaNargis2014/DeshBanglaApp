package com.deshbangla.shrimpandfish.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.deshbangla.shrimpandfish.adapter.CategoryAdapter;
import com.deshbangla.shrimpandfish.adapter.FeaturedSliderAdapter;
import com.deshbangla.shrimpandfish.adapter.OfferAdapter;
import com.deshbangla.shrimpandfish.adapter.ProductsAdapter;
import com.deshbangla.shrimpandfish.customViewPager.CustomViewpager;
import com.deshbangla.shrimpandfish.model.banner.Banner;
import com.deshbangla.shrimpandfish.model.products.Datum;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.activities.CategoryItemsActivity;
import com.deshbangla.shrimpandfish.viewmodel.BannerViewModel;
import com.deshbangla.shrimpandfish.viewmodel.CategoryViewModel;
import com.deshbangla.shrimpandfish.viewmodel.FeaturedProductViewModel;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private CustomViewpager featuredSlider, offerSlider;
    private RecyclerView categoryView, topSellingView;
    private DotsIndicator dotsIndicator, dots_indicator2;
    private AppCompatButton viewAll, viewAll1;

    private FeaturedProductViewModel featuredProductViewModel;
    private CategoryViewModel categoryViewModel;
    private BannerViewModel bannerViewModel;

    private List<Datum> featuredItemsList = new ArrayList<>();
    private List<com.deshbangla.shrimpandfish.model.category.Datum> categories = new ArrayList<>();
    private List<Banner> bannerList = new ArrayList<>();

    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    private Timer timer = new Timer();
    private CategoryAdapter categoryAdapter;
    private FeaturedSliderAdapter featuredSliderAdapter;
    private OfferAdapter offerAdapter;
    private ProductsAdapter productsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        featuredProductViewModel = ViewModelProviders.of(this).get(FeaturedProductViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        bannerViewModel = ViewModelProviders.of(this).get(BannerViewModel.class);

//        Timer timer1 = new Timer();

        progressBar = view.findViewById(R.id.progressBar);
        nestedScrollView = view.findViewById(R.id.nestedScroll);
        featuredSlider = view.findViewById(R.id.customViewPager);
//        offerSlider = view.findViewById(R.id.customViewPager2);
        categoryView = view.findViewById(R.id.recyclerView);
        topSellingView = view.findViewById(R.id.featuredItems);
        dotsIndicator = view.findViewById(R.id.dots_indicator);
//        dots_indicator2 = view.findViewById(R.id.dots_indicator2);
        viewAll = view.findViewById(R.id.viewAll);
        viewAll1 = view.findViewById(R.id.viewAll1);

        viewAll1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CategoryItemsActivity.class);
            intent.putExtra("type", "all");
            intent.putExtra("title", "All Fish");
            if (getContext() != null){
                getContext().startActivity(intent);
                ((Activity)getContext()).finish();
            }
        });

        viewAll.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CategoryItemsActivity.class);
            intent.putExtra("type", "all");
            intent.putExtra("title", "All Fish");
            if (getContext() != null){
                getContext().startActivity(intent);
                ((Activity)getContext()).finish();
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
        categoryView.setLayoutManager(linearLayoutManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        topSellingView.setLayoutManager(gridLayoutManager);

        categoryAdapter = new CategoryAdapter(getContext(), categories);
        categoryView.setAdapter(categoryAdapter);

        productsAdapter = new ProductsAdapter(getContext(), featuredItemsList, 1);
        topSellingView.setAdapter(productsAdapter);
        topSellingView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!topSellingView.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getFeaturedData();
                    }
                }
            }
        });



        featuredSliderAdapter = new FeaturedSliderAdapter(getContext(), bannerList);
//        offerAdapter = new OfferAdapter(getContext());

//        offerSlider.setAdapter(offerAdapter);
//        dots_indicator2.setViewPager(offerSlider);

        featuredSlider.setAdapter(featuredSliderAdapter);
        dotsIndicator.setViewPager(featuredSlider);

//        timer1.scheduleAtFixedRate(new MyTimerTask(offerSlider, offerAdapter.getCount()),3000,4000);

        progressBar.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
        getBanners();
        getCategories();
        getFeaturedData();

        return view;
    }

    public void getFeaturedData(){
        featuredProductViewModel.getFeaturedProducts(currentPage).observe(getViewLifecycleOwner(), ProductResponse -> {
            progressBar.setVisibility(View.GONE);
            nestedScrollView.setVisibility(View.VISIBLE);
            if (ProductResponse != null){
                totalAvailablePages = ProductResponse.getMeta().getLastPage();
                if (ProductResponse.getData() != null){
                    int OldCount = featuredItemsList.size();
                    featuredItemsList.addAll(ProductResponse.getData());
                    productsAdapter.notifyItemRangeInserted(OldCount, featuredItemsList.size());
                }
            }
        });
    }

    public void getCategories(){
        categoryViewModel.getCategory().observe(getViewLifecycleOwner(), CategoryResponse -> {
            if (CategoryResponse != null){
                categories.addAll(CategoryResponse.getData());
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getBanners(){
        bannerViewModel.getBanners().observe(getViewLifecycleOwner(), BannerResponse -> {
            if (BannerResponse != null){
                bannerList.addAll(BannerResponse.getBanner());
                bannerList.addAll(BannerResponse.getBannersecond());
                featuredSliderAdapter.notifyDataSetChanged();
                timer.scheduleAtFixedRate(new MyTimerTask(featuredSlider, featuredSliderAdapter.getCount()),2000,3000);
            }
        });
    }

    public class MyTimerTask extends TimerTask {

        CustomViewpager custom;
        int count;

        public MyTimerTask(CustomViewpager custom, int count) {
            this.custom = custom;
            this.count = count;
        }

        @Override
        public void run() {
            Activity activity = getActivity();
            if (activity != null){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (custom.getCurrentItem() < count - 1) {
                            custom.setCurrentItem(custom.getCurrentItem() + 1, true);
                        } else {
                            custom.setCurrentItem(0, true);
                        }
                    }
                });
            }
        }
    }

}