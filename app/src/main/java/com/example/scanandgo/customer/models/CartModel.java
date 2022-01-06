package com.example.scanandgo.customer.models;

import android.media.Image;

public class CartModel {
    String productName,totalQuantity,productPrice;
    Image productImage;
    int totalPrice;

    public CartModel() {
    }

    public CartModel(String productName, String totalQuantity, String productPrice, Image productImage, int totalPrice) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
