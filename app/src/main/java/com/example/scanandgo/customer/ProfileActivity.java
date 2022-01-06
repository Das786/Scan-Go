package com.example.scanandgo.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.scanandgo.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilebackbtn;
    EditText profileNameEdt,profileEmailEdt,profilePhoneEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilebackbtn = findViewById(R.id.profilebackbtn);

        profileNameEdt  = findViewById(R.id.profileNameEdt);
        profileEmailEdt   = findViewById(R.id.profileEmailEdt);
        profilePhoneEdt  = findViewById(R.id.profilePhoneEdt);

        profileNameEdt.setEnabled(false);
        profileEmailEdt.setEnabled(false);
        profilePhoneEdt.setEnabled(false);




        profilebackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}