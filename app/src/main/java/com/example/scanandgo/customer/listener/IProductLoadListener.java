package com.example.scanandgo.customer.listener;

import com.example.scanandgo.admin.models.AddProductAdminModel;
import com.example.scanandgo.customer.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public interface IProductLoadListener {
    void OnProductLoadSuccess(ArrayList<ProductModel> productModelArrayList);
    void OnProductLoadFailed(String message);
}
