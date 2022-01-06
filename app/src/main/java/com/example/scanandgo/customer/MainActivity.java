package com.example.scanandgo.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.scanandgo.Login;
import com.example.scanandgo.LogoutFargment;
import com.example.scanandgo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton  qrCode;
    public static TextView scantext;
    FirebaseAuth fAuth;
    AlertDialog.Builder builder;
    String[] permission ={
            Manifest.permission.CAMERA
    };
    int PERM_CODE =11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        qrCode = (FloatingActionButton) findViewById(R.id.qrCode);
        builder = new AlertDialog.Builder(this);
        fAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNav = findViewById(R.id.bottomAppBar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new DashboardFargment()).commit();


        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScannerView.class));
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;

                    switch (item.getItemId()){
                        case R.id.dashboard:
                            selectFragment = new DashboardFargment();
                            break;
                        case R.id.cart:
                            selectFragment = new CartFargment();
                            break;
                        case R.id.product:
                            selectFragment = new ProductFargment();
                            break;
                        case R.id.logoutBtn:
                            selectFragment = new LogoutFargment();
                            builder.setMessage("Do you want to Logout?")
                                    .setCancelable(false)
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(getApplicationContext(), Login.class));
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            AlertDialog  alert = builder.create();
                            alert.setTitle("Logout");
                            alert.show();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectFragment).commit();
                    return true;
                }
            };




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private boolean checkpermission(){
        List<String> listofpermission = new ArrayList<>();
        for(String perm: permission){
            if (ContextCompat.checkSelfPermission(getApplicationContext(),perm) != PackageManager.PERMISSION_GRANTED){
                listofpermission.add(perm);
            }
            }
        if (!listofpermission.isEmpty()){
            ActivityCompat.requestPermissions(this,listofpermission.toArray(new String[listofpermission.size()]),PERM_CODE );
            return  false;

        }
        return  true;
    }
}