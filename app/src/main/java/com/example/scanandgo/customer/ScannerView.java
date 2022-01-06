package com.example.scanandgo.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import com.budiyev.android.codescanner.DecodeCallback;
import com.example.scanandgo.R;
import com.example.scanandgo.customer.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class ScannerView extends AppCompatActivity {

   CodeScanner codeScanner;
   CodeScannerView codeScannerView;
   TextView restext,price;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_view);

        codeScannerView =(CodeScannerView) findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this,codeScannerView);
        restext = findViewById(R.id.restext);
        price = findViewById(R.id.product_price);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String Result = result.getText();
                        StringBuilder stringBuilder = new StringBuilder(Result);

                        StringBuilder AResult = stringBuilder.delete(0,7);


//                    firestore.collection("Product Details").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if (task.isSuccessful()){
//                                DocumentSnapshot documentSnapshot = task.getResult();
//
//                                String productId = documentSnapshot.getString("product_id");
//
//                                if (Result == productId){
//                                    restext.setText(Result);
//                                    Toast.makeText(getApplicationContext(), "Product Added", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    restext.setText("Failed Product Not Found");
//                                    Toast.makeText(getApplicationContext(), ""+productId, Toast.LENGTH_SHORT).show();
////                                    Toast.makeText(getApplicationContext(), Result+" Product Not Found", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


                        firestore.collection("Product Details").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot ds : task.getResult()) {
                                        if (ds.getString("product_id").equals(AResult.toString())) {
                                            String saveCurrentTime, SaveCurrentDate;

                                            Calendar calForDate = Calendar.getInstance();

                                            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
                                            saveCurrentTime = currentDate.format(calForDate.getTime());

                                            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                                            SaveCurrentDate = currentTime.format(calForDate.getTime());

                                            final HashMap<String,Object> cartMap = new HashMap<>();


                                            cartMap.put("productName",ds.getString("productName"));
                                            cartMap.put("currentTime",saveCurrentTime);
                                            cartMap.put("currentDate",SaveCurrentDate);
                                            cartMap.put("productPrice",ds.getString("productPrice"));
                                            cartMap.put("totalQuantity","1");

                                            String totalprice = ds.getString("productPrice");
                                            int totalprice1 = Integer.parseInt(totalprice);
                                            cartMap.put("totalPrice",totalprice1);

                                            firestore.getInstance().collection("AddToCart").document(auth.getCurrentUser().getUid())
                                                    .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                    Toast.makeText(getApplicationContext(), "Added To Cart Successfully", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        }
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Failed to complete the Task", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


//                        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
//                                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                Toast.makeText(getApplicationContext(), "Added To Cart Successfully", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });

                        Toast.makeText(ScannerView.this,""+result.getText(),Toast.LENGTH_SHORT);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCamera();
    }

    private void requestCamera() {
        codeScanner.startPreview();
    }
}