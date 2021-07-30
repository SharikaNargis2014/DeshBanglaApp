package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.SearchedItemRepository;
import com.deshbangla.shrimpandfish.response.SearchResponse;

public class SearchedItemViewModel extends ViewModel {

    private SearchedItemRepository searchedItemRepository;

    public SearchedItemViewModel(){
        searchedItemRepository = new SearchedItemRepository();
    }

    public LiveData<SearchResponse> getSearchedItem(String searchedItem, int page){
        return searchedItemRepository.getSearchedItem(searchedItem, page);
    }

}
