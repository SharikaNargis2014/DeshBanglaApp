package com.deshbangla.shrimpandfish.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Href {

    @SerializedName("product_link")
    @Expose
    private String productLink;

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

}
