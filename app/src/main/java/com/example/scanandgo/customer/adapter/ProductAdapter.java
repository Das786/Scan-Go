package com.example.scanandgo.customer.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.scanandgo.admin.models.AddProductAdminModel;
import com.example.scanandgo.customer.ProductFargment;
import com.example.scanandgo.customer.models.ProductModel;
import com.example.scanandgo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Converter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.myviewholder>{

    ProductFargment productFargment;
    ArrayList<ProductModel> dataholder;
    FirebaseAuth auth;
    public FirebaseFirestore firestore;


    public ProductAdapter(ProductFargment productFargment, ArrayList<ProductModel> dataholder) {
        this.productFargment = productFargment;
        this.dataholder = dataholder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_products,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.myviewholder holder, int position) {
        StringBuilder builder = new StringBuilder();
        Glide.with(productFargment)
                .load(dataholder.get(position).getImage())
                .into(holder.img);

        holder.header.setText(dataholder.get(position).getProductName());
        holder.desc.setText(dataholder.get(position).getProductDesc());
        holder.price.setText(dataholder.get(position).getProductPrice());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView img,incre_btn,decre_btn;
        TextView header,desc,price,product_quantity;

        int totalQuantity =1;
        int totalPrice;

        public myviewholder(@NonNull View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.img_product);
            header = itemView.findViewById(R.id.tv_product_name);
            desc = itemView.findViewById(R.id.tv_description);
            price = itemView.findViewById(R.id.product_price);
            incre_btn = itemView.findViewWithTag(R.id.increment_btn);
            decre_btn = itemView.findViewById(R.id.decrement_btn);
            product_quantity = itemView.findViewById(R.id.product_quantity);





            itemView.findViewById(R.id.increment_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    totalPrice = Integer.parseInt(price.getText().toString());
                    if ( totalQuantity < 10 ){
                        totalQuantity++;
                        product_quantity.setText(String.valueOf(totalQuantity));
                        totalPrice = Integer.parseInt(price.getText().toString()) * totalQuantity;
                    }
                    else{
                        Toast.makeText(itemView.getContext(), "Max limited Reached", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            itemView.findViewById(R.id.decrement_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    totalPrice = Integer.parseInt(price.getText().toString());

                    if (totalQuantity > 1 ){
                        totalQuantity--;
                        product_quantity.setText(String.valueOf(totalQuantity));
                        totalPrice = Integer.parseInt(price.getText().toString()) * totalQuantity;
                    }

                }
            });
            
            itemView.findViewById(R.id.img_add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   String saveCurrentTime, SaveCurrentDate;

                    Calendar calForDate = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                    saveCurrentTime = currentDate.format(calForDate.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    SaveCurrentDate = currentTime.format(calForDate.getTime());


                    final HashMap<String,Object> cartMap = new HashMap<>();


                    if (totalPrice == 0){
                        totalPrice = Integer.parseInt(price.getText().toString());
                    }




                    cartMap.put("productName",header.getText().toString());
                    cartMap.put("currentTime",saveCurrentTime);
                    cartMap.put("currentDate",SaveCurrentDate);
                    cartMap.put("productPrice",price.getText().toString());
                    cartMap.put("totalQuantity",product_quantity.getText().toString());
                    cartMap.put("totalPrice", totalPrice);

                    firestore.getInstance().collection("AddToCart").document(auth.getCurrentUser().getUid())
                            .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(v.getContext(), "Added To Cart Successfully", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            });
        }
        
    }

}