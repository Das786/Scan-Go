package com.example.scanandgo.customer.listener;

import com.example.scanandgo.customer.models.CartModel;
import com.example.scanandgo.customer.models.ProductModel;

import java.util.ArrayList;

public interface ICartLoadListener {
    void OnCartLoadSuccess(ArrayList<CartModel> cartModelArrayList);
    void OnCartLoadFailed(String message);
}
