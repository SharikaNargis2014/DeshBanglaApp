package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.ProductDetailsRepository;
import com.deshbangla.shrimpandfish.response.ProductDetailsResponse;

public class ProductDetailsViewModel extends ViewModel {
    private ProductDetailsRepository productDetailsRepository;

    public ProductDetailsViewModel(){
        productDetailsRepository = new ProductDetailsRepository();
    }

    public LiveData<ProductDetailsResponse> getProductDetails(int id){
        return productDetailsRepository.getProductDetails(id);
    }
}
