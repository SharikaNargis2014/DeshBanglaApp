package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.AllProductsRepository;
import com.deshbangla.shrimpandfish.response.ProductResponse;

public class AllProductsViewModel extends ViewModel {

    private AllProductsRepository allProductsRepository;

    public AllProductsViewModel(){
        allProductsRepository = new AllProductsRepository();
    }

    public LiveData<ProductResponse> getAllProducts(int page){
        return allProductsRepository.getAllProducts(page);
    }
}
