package com.example.scanandgo.admin.models;

public class AddProductAdminModel {

    String product_id,productName,productDesc,prodCateogry,productQuantiy,productPrice,image;

    public AddProductAdminModel() {
    }

    public AddProductAdminModel(String product_id, String productName, String productDesc, String prodCateogry, String productQuantiy, String productPrice, String image) {
        this.product_id = product_id;
        this.productName = productName;
        this.productDesc = productDesc;
        this.prodCateogry = prodCateogry;
        this.productQuantiy = productQuantiy;
        this.productPrice = productPrice;
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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
