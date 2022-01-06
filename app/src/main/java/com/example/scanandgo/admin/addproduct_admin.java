package com.example.scanandgo.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.scanandgo.admin.models.AddProductAdminModel;
import com.example.scanandgo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

public class addproduct_admin extends AppCompatActivity {

    private EditText productNameEdt,productDescEdt,prodCateogryEdt,productQuantiyEdt,productPriceEdt,productImageEdt;
    private String productName,productDesc,prodCateogry,productQuantiy,productPrice,productImage;
    Button AddProduct, BackProduct,photobrowse;
    Uri filepath;
    Bitmap bitmap;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct_admin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        productNameEdt = findViewById(R.id.productName);
        productDescEdt = findViewById(R.id.productDesc);
        prodCateogryEdt = findViewById(R.id.prodCateogry);
        productQuantiyEdt = findViewById(R.id.productQuantiy);
        productPriceEdt = findViewById(R.id.productPrice);
        productImageEdt = findViewById(R.id.productImage);
        AddProduct = findViewById(R.id.addProduct);
        BackProduct = findViewById(R.id.backProduct);
        photobrowse = findViewById(R.id.photobrowse);


        photobrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(addproduct_admin.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select the Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });




        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                checkField(productNameEdt);
                checkField(productDescEdt);
                checkField(prodCateogryEdt);
                checkField(productQuantiyEdt);
                checkField(productPriceEdt);


                productName = productNameEdt.getText().toString();
                productDesc = productDescEdt.getText().toString();
                prodCateogry = prodCateogryEdt.getText().toString();
                productQuantiy = productQuantiyEdt.getText().toString();
                productPrice = productPriceEdt.getText().toString();


                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference uploader = storage.getReference("Image1"+new Random().nextInt(50));

                uploader.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        if(valid){
                                            CollectionReference productCourse = fStore.collection("Product Details");
                                            String id = UUID.randomUUID().toString();
                                            AddProductAdminModel addProductAdminModel = new AddProductAdminModel(id,productName,productDesc,prodCateogry,productQuantiy,productPrice,uri.toString());

                                            productCourse.add(addProductAdminModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(addproduct_admin.this, "Product are Added Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(addproduct_admin.this, "Failed to add Product :"+ e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        }


                                    }
                                });

                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        Toast.makeText(getApplicationContext(), "Uploaded :"+(int)percent+"%", Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });


        BackProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminDash.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            filepath=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                productImageEdt.setText(filepath.toString());

            }catch (Exception e){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
        return valid;


    }
}