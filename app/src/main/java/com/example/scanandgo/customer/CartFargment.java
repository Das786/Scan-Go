package com.example.scanandgo.customer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.adapter.CartAdapter;
import com.example.scanandgo.customer.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class CartFargment extends Fragment {


    TextView CartTotal,BuyNow;
    RecyclerView recyclerView;
    ArrayList<CartModel> cartModelArrayList;
    CartAdapter cartAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart,container,false);




        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();




        BuyNow = view.findViewById(R.id.cart_buy_now);
        CartTotal = view.findViewById(R.id.cart_total_price);
        recyclerView = view.findViewById(R.id.cartRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartModelArrayList = new ArrayList<>();
        cartAdapter = new CartAdapter(this,cartModelArrayList);
        recyclerView.setAdapter(cartAdapter);






        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int totalBill = intent.getIntExtra("totalAmount",0);
                CartTotal.setText("Total : ₹"+totalBill);




            }
        };

        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));






        BuyNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                String s = CartTotal.getText().toString();
                StringBuilder stringBuilder = new StringBuilder(s);
                stringBuilder.delete(0,9);
                Intent intent1 = new Intent(getActivity(),Payment_Section.class);
                intent1.putExtra("Amount", (Serializable) stringBuilder );
                startActivity(intent1);

            }

        });

        firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        Log.e("TAG",""+doc);
                        CartModel cartModel = doc.toObject(CartModel.class);
                        cartModelArrayList.add(cartModel);
                    }
                    cartAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;

    }

}
