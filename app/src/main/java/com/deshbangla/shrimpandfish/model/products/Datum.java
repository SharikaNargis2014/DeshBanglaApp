package com.deshbangla.shrimpandfish.model.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("cat_slug")
    @Expose
    private String catSlug;

    @SerializedName("item_name")
    @Expose
    private String itemName;

    @SerializedName("item_slug")
    @Expose
    private String itemSlug;

    @SerializedName("item_image")
    @Expose
    private String itemImage;

    @SerializedName("item_weight")
    @Expose
    private String itemWeight;

    @SerializedName("item_des")
    @Expose
    private String itemDes;

    @SerializedName("purchase_price")
    @Expose
    private String purchasePrice;

    @SerializedName("sell_price")
    @Expose
    private String sellPrice;

    @SerializedName("stock")
    @Expose
    private String stock;

    @SerializedName("item_status")
    @Expose
    private String itemStatus;

    @SerializedName("href")
    @Expose
    private Href href;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatSlug() {
        return catSlug;
    }

    public void setCatSlug(String catSlug) {
        this.catSlug = catSlug;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSlug() {
        return itemSlug;
    }

    public void setItemSlug(String itemSlug) {
        this.itemSlug = itemSlug;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(String itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
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

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Href getHref() {
        return href;
    }

    public void setHref(Href href) {
        this.href = href;
    }

}
