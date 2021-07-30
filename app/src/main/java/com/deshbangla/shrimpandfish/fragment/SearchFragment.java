package com.deshbangla.shrimpandfish.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deshbangla.shrimpandfish.adapter.RelatedItemsAdapter;
import com.deshbangla.shrimpandfish.model.productDetails.Products;
import com.deshbangla.shrimpandfish.R;
import com.deshbangla.shrimpandfish.viewmodel.SearchedItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView searchedItemsRecycler;
    private RelatedItemsAdapter adapter;
    private List<Products> searchedItems = new ArrayList<>();
    private String searchedItem;
    private SearchedItemViewModel searchedItemViewModel;

    private int currentPage = 1;
    private int totalAvailablePages = 1;


    public SearchFragment(String searchedItem){
        this.searchedItem = searchedItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchedItemViewModel = ViewModelProviders.of(this).get(SearchedItemViewModel.class);

        searchedItemsRecycler = view.findViewById(R.id.searchedItems);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        searchedItemsRecycler.setLayoutManager(gridLayoutManager);

        adapter = new RelatedItemsAdapter(getContext(), searchedItems);
        searchedItemsRecycler.setAdapter(adapter);
        searchedItemsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searchedItemsRecycler.canScrollVertically(1)){
                    if (currentPage <= totalAvailablePages){
                        currentPage += 1;
                        getSearchedItems();
                    }
                }
            }
        });

        getSearchedItems();

        return view;
    }

    private void getSearchedItems() {
        searchedItemViewModel.getSearchedItem(searchedItem, currentPage).observe(getViewLifecycleOwner(), SearchResponse -> {
            if (SearchResponse != null){
                totalAvailablePages = SearchResponse.getSearch().getLastPage();
                if (SearchResponse.getSearch().getData() != null){
                    int OldCount = adapter.getItemCount();
                    searchedItems.addAll(SearchResponse.getSearch().getData());
                    adapter.notifyItemRangeInserted(OldCount, searchedItems.size());
                }

            }
        });
    }
}