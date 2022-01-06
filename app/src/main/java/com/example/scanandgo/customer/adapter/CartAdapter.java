package com.example.scanandgo.customer.adapter;

import static android.os.Build.VERSION_CODES.R;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.scanandgo.R;
import com.example.scanandgo.customer.CartFargment;
import com.example.scanandgo.customer.Payment_Section;
import com.example.scanandgo.customer.models.CartModel;
import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    CartFargment context;
    Context c;
    ArrayList<CartModel> list;
    int totalAmount = 0;


    public CartAdapter(CartFargment context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.scanandgo.R.layout.my_cart_items,parent,false);
        return new CartAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText("Price: ₹"+list.get(position).getProductPrice());
        holder.totalQuantity.setText("Total Quantity: "+list.get(position).getTotalQuantity());


        totalAmount = totalAmount + list.get(position).getTotalPrice();

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);



        LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productName, price , totalQuantity, totalPrice;
        ImageView cart_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(com.example.scanandgo.R.id.cart_product_name);
            price = itemView.findViewById(com.example.scanandgo.R.id.cart_product_price);
            totalQuantity = itemView.findViewById(com.example.scanandgo.R.id.cart_quantity);
            totalPrice = itemView.findViewById(com.example.scanandgo.R.id.cart_total_price);
            cart_img = itemView.findViewById(com.example.scanandgo.R.id.cart_img_product);


        }
    }
}
