package com.example.scanandgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scanandgo.admin.AdminDash;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fullName,email,password,phone;
    Button registerBtn,goToLogin;
    CheckBox isAdmin,isUser;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        phone = findViewById(R.id.registerPhone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.gotoLogin);

        isAdmin = findViewById(R.id.isAdmin);
        isUser = findViewById(R.id.isUser);


        isUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    isAdmin.setChecked(false);
                }
            }
        });

        isAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    isUser.setChecked(false);
                }
            }
        });



        registerBtn.setOnClickListener(view -> {
            checkField(fullName);
            checkField(email);
            checkField(password);
            checkField(phone);


            if(!(isAdmin.isChecked() || isUser.isChecked())){
                Toast.makeText(Register.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                return;
            }

            if(valid){
                fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(authResult -> {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(Register.this,"Account Created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String,Object> userInfo = new HashMap<>();
                        userInfo.put("Fullname", fullName.getText().toString());
                        userInfo.put("UserEmail",email.getText().toString());
                        userInfo.put("PhoneNumber",phone.getText().toString());
                        if(isAdmin.isChecked()){
                            userInfo.put("isAdmin", "1");
                            startActivity(new Intent(getApplicationContext(), AdminDash.class));
                            finish();
                        }
                        if(isUser.isChecked()){
                            userInfo.put("isUser", "1");
                            startActivity(new Intent(getApplicationContext(),Onboarding_Screen.class));
                            finish();
                        }
                        df.set(userInfo);


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
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