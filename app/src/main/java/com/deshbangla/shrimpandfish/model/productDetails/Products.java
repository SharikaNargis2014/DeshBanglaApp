package com.deshbangla.shrimpandfish.model.productDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("category_slug")
    @Expose
    private String categorySlug;

    @SerializedName("subcategory_slug")
    @Expose
    private String subcategorySlug;

    @SerializedName("store_id")
    @Expose
    private String storeId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("product_slug")
    @Expose
    private String productSlug;

    @SerializedName("desp")
    @Expose
    private String desp;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("purchase_price")
    @Expose
    private String purchasePrice;

    @SerializedName("sell_price")
    @Expose
    private String sellPrice;

    @SerializedName("stock")
    @Expose
    private String stock;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

    public String getSubcategorySlug() {
        return subcategorySlug;
    }

    public void setSubcategorySlug(String subcategorySlug) {
        this.subcategorySlug = subcategorySlug;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductSlug() {
        return productSlug;
    }

    public void setProductSlug(String productSlug) {
        this.productSlug = productSlug;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
