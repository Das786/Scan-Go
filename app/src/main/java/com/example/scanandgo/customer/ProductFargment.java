package com.example.scanandgo.customer;

import static android.widget.Toast.makeText;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.adapter.ProductAdapter;
import com.example.scanandgo.customer.listener.ICartLoadListener;
import com.example.scanandgo.customer.listener.IProductLoadListener;
import com.example.scanandgo.customer.models.CartModel;
import com.example.scanandgo.customer.models.ProductModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class ProductFargment extends Fragment implements IProductLoadListener, ICartLoadListener {



    RecyclerView recyclerView;
    ArrayList<ProductModel> dataholder;
    ProductAdapter adapter;
    ConstraintLayout constraintLayout;

    IProductLoadListener productLoadListener;
    ICartLoadListener cartLoadListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_product,container,false);

        productLoadListener = this;
        cartLoadListener = this;


        constraintLayout = view.findViewById(R.id.constraintLayout);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataholder = new ArrayList<>();
        adapter = new ProductAdapter(this,dataholder);
        recyclerView.setAdapter(adapter);

//        loadProductFromFirebase();
        loadStoreProductFromFirebase();


//        ProductModel obj1 = new ProductModel("1",R.drawable.wheat_flour, "Wheat","This is Random Description");
//        dataholder.add(obj1);
//
//        recyclerView.setAdapter(new ProductAdapter(dataholder));

        return view;


    }




    private void loadStoreProductFromFirebase() {
        ArrayList<ProductModel> dataholder= new ArrayList<>();

        FirebaseFirestore.getInstance().collection("Product Details")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(getContext(), ""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                dataholder.add(dc.getDocument().toObject(ProductModel.class));
                            }
                            productLoadListener.OnProductLoadSuccess(dataholder);
                        }
                    }
                });
    }

//    private void loadProductFromFirebase() {
//        ArrayList<ProductModel> dataholder= new ArrayList<>();
//
//
//                FirebaseDatabase.getInstance().getReference("Drink")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if (snapshot.exists()){
//                                    for (DataSnapshot productSnapshot: snapshot.getChildren()){
//                                        ProductModel productModel = productSnapshot.getValue(ProductModel.class);
//                                        productModel.setKey(productSnapshot.getKey());
//                                        dataholder.add(productModel);
//                                    }
//                                    productLoadListener.OnProductLoadSuccess(dataholder);
//                                }
//                                else
//                                    productLoadListener.OnProductLoadFailed("Can't find the product");
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                productLoadListener.OnProductLoadFailed(error.getMessage());
//                                Log.e("",error.getMessage());
//                            }
//                        });
//    }


    @Override
    public void OnProductLoadSuccess(ArrayList<ProductModel> productModelArrayList) {
        ProductAdapter adapter = new ProductAdapter(this,productModelArrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnProductLoadFailed(String message) {
        Snackbar.make(constraintLayout,message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void OnCartLoadSuccess(ArrayList<CartModel> cartModelArrayList) {

    }

    @Override
    public void OnCartLoadFailed(String message) {

    }
}
