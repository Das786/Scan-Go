package com.example.scanandgo.customer.models;

public class ProductModel {


    private String productName,productDesc,prodCateogry,productQuantiy,productPrice,image;

    public ProductModel() {
    }

    public ProductModel(String productName, String productDesc, String prodCateogry, String productQuantiy, String productPrice, String image) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.prodCateogry = prodCateogry;
        this.productQuantiy = productQuantiy;
        this.productPrice = productPrice;
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProdCateogry() {
        return prodCateogry;
    }

    public void setProdCateogry(String prodCateogry) {
        this.prodCateogry = prodCateogry;
    }

    public String getProductQuantiy() {
        return productQuantiy;
    }

    public void setProductQuantiy(String productQuantiy) {
        this.productQuantiy = productQuantiy;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
