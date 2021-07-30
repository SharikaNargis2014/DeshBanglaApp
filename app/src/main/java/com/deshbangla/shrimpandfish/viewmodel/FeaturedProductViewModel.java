package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.FeaturedProductRepository;
import com.deshbangla.shrimpandfish.response.ProductResponse;

public class FeaturedProductViewModel extends ViewModel {

    private FeaturedProductRepository featuredProductRepository;

    public FeaturedProductViewModel(){
        featuredProductRepository = new FeaturedProductRepository();
    }

    public LiveData<ProductResponse> getFeaturedProducts(int page){
        return featuredProductRepository.getFeaturedProducts(page);
    }

}
