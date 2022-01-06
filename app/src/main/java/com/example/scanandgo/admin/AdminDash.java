package com.example.scanandgo.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scanandgo.Login;
import com.example.scanandgo.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDash extends AppCompatActivity {

    Button AdminlogoutBtn, AddProductBtn, ShowProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        AddProductBtn = findViewById(R.id.btnaddProduct);
        ShowProductBtn = findViewById(R.id.btnshowProduct);
        AdminlogoutBtn = findViewById(R.id.AdminlogoutBtn);

        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addproduct_admin.class));
                finish();
            }
        });


        AdminlogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        ShowProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), showproduct_admin.class));
                finish();
            }
        });
    }
}