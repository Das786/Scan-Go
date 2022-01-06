package com.example.scanandgo.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.scanandgo.admin.models.AddProductAdminModel;
import com.example.scanandgo.R;
import com.example.scanandgo.admin.adapter.ShowProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class showproduct_admin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AddProductAdminModel> datalist;
    FirebaseFirestore db;
    ShowProductAdapter adapter;
    ImageView delete_btn;




//     int images[] ={R.drawable.cold_drink,R.drawable.coffee,R.drawable.briyani_rice,R.drawable.baking_soda,R.drawable.coridanar_powder,
//             R.drawable.green_tea,R.drawable.milk,R.drawable.sugar,R.drawable.vermicelli,R.drawable.vingear,R.drawable.wheat_flour};

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),AdminDash.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showproduct_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter = new ShowProductAdapter(datalist);
        recyclerView.setAdapter(adapter);
        delete_btn = findViewById(R.id.prd_delete_btn);

        db = FirebaseFirestore.getInstance();
        db.collection("Product Details").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d: list)
                        {
                            AddProductAdminModel obj = d.toObject(AddProductAdminModel.class);
                            datalist.add(obj);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });


        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        db.collection("Product Details")
                                .whereEqualTo("product_id",datalist.get(viewHolder.getAdapterPosition()).getProduct_id())
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();
                                    db.collection("Product Details")
                                            .document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                                    datalist.remove(viewHolder.getAdapterPosition());
                                                    adapter.notifyDataSetChanged();
                                            }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).attachToRecyclerView(recyclerView);




    }


}