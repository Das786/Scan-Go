package com.example.scanandgo.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanandgo.R;
import com.example.scanandgo.admin.models.AddProductAdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowProductAdapter extends RecyclerView.Adapter<ShowProductAdapter.myviewholder> {


    ArrayList<AddProductAdminModel> datalist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ShowProductAdapter(ArrayList<AddProductAdminModel> datalist) {
        this.datalist = datalist;
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_rows,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        AddProductAdminModel addProductAdminModel = datalist.get(position);
        holder.ProductName.setText(datalist.get(position).getProductName());
        holder.ProductDesc.setText(datalist.get(position).getProductDesc());
          String imageUri = null;
          imageUri= addProductAdminModel.getImage();
        Picasso.get().load(imageUri).into(holder.ProductImages);

        holder.ProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Product Details")
                        .whereEqualTo("product_id",datalist.get(holder.getAdapterPosition()).getProduct_id())
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
                                            Toast.makeText(v.getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                            datalist.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(v.getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (datalist != null) {
            return datalist.size();
        }
        return 0;
    }

    class  myviewholder extends RecyclerView.ViewHolder{
        TextView ProductName, ProductDesc;
        ImageView ProductImages,ProductDelete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ProductImages = itemView.findViewById(R.id.img_product);
            ProductName =itemView.findViewById(R.id.tv_product_name);
            ProductDesc = itemView.findViewById(R.id.tv_description);
            ProductDelete = itemView.findViewById(R.id.prd_delete_btn);

        }
    }
}
