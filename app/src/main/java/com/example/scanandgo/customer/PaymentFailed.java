package com.example.scanandgo.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.scanandgo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentFailed extends AppCompatActivity {

    Button backCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failed);

        backCart = findViewById(R.id.backcart);

        backCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCart.setEnabled(false);
                backCart.setVisibility(View.INVISIBLE);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.failedmainactivity,new CartFargment()).commit();
            }
        });
    }
}