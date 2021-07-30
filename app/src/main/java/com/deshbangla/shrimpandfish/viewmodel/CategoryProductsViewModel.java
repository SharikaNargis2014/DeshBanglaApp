package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.CategoryProductsRepository;
import com.deshbangla.shrimpandfish.response.ProductResponse;

public class CategoryProductsViewModel extends ViewModel {

    private CategoryProductsRepository categoryProductsRepository;

    public CategoryProductsViewModel(){
        categoryProductsRepository = new CategoryProductsRepository();
    }

    public LiveData<ProductResponse> getCategoryProducts(String slug, int page){
        return categoryProductsRepository.getCategoryProducts(slug, page);
    }

}
