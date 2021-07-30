package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.CategoryRepository;
import com.deshbangla.shrimpandfish.response.CategoryResponse;

public class CategoryViewModel extends ViewModel {

    private CategoryRepository categoryRepository;

    public CategoryViewModel(){
        categoryRepository = new CategoryRepository();
    }

    public LiveData<CategoryResponse> getCategory(){
        return categoryRepository.getCategories();
    }

}
