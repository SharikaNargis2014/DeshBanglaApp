package com.deshbangla.shrimpandfish.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.deshbangla.shrimpandfish.repository.BannerRepository;
import com.deshbangla.shrimpandfish.response.BannerResponse;

public class BannerViewModel extends ViewModel {

    private BannerRepository bannerRepository;

    public BannerViewModel(){
        bannerRepository = new BannerRepository();
    }

    public LiveData<BannerResponse> getBanners(){
        return bannerRepository.getBanners();
    }
}
