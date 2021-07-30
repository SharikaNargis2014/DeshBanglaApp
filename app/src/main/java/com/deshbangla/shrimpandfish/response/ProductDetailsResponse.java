package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.productDetails.Products;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailsResponse {

    @SerializedName("products")
    @Expose
    private Products products;

    @SerializedName("related_fish")
    @Expose
    private List<Products> relatedFish = null;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public List<Products> getRelatedFish() {
        return relatedFish;
    }

    public void setRelatedFish(List<Products> relatedFish) {
        this.relatedFish = relatedFish;
    }
}
