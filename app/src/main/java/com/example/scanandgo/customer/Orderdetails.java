package com.example.scanandgo.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.adapter.PaymentAdapter;
import com.example.scanandgo.customer.models.CartModel;
import com.example.scanandgo.customer.models.PaymentModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Orderdetails extends AppCompatActivity {

    ImageView orderback;
    RecyclerView recyclerView;
    PaymentAdapter paymentAdapter;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ArrayList<PaymentModel> paymentModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

        orderback = findViewById(R.id.orderback);
        recyclerView = findViewById(R.id.paymentRecyclerView);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        setRecyclerView();

        orderback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentModelArrayList = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(this,paymentModelArrayList);
        recyclerView.setAdapter(paymentAdapter);


        firestore.collection("PaymentDetails").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        PaymentModel paymentModel = doc.toObject(PaymentModel.class);
                        paymentModelArrayList.add(paymentModel);
                    }
                    paymentAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }






}