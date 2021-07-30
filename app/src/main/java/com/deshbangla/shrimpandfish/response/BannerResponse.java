package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.banner.Banner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResponse {
    @SerializedName("banner")
    @Expose
    private List<Banner> banner = null;
    @SerializedName("bannersecond")
    @Expose
    private List<Banner> bannersecond = null;

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Banner> getBannersecond() {
        return bannersecond;
    }

    public void setBannersecond(List<Banner> bannersecond) {
        this.bannersecond = bannersecond;
    }
}
