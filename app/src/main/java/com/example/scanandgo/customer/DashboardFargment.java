package com.example.scanandgo.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.scanandgo.Onboarding_Screen;
import com.example.scanandgo.R;

public class DashboardFargment extends Fragment {

    public DashboardFargment(){

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        return view;
    }

    @Override
    public  void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout todolist,orderpage,aboutConstraint,profileActivity;


        todolist = view.findViewById(R.id.todolist);
        orderpage = view.findViewById(R.id.orderpage);
        aboutConstraint = view.findViewById(R.id.aboutConstraint);
        profileActivity = view.findViewById(R.id.profileActivity);


        profileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });



        todolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),TodoGoceryList.class));
            }
        });

        orderpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Orderdetails.class));
            }
        });

        aboutConstraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Onboarding_Screen.class));
            }
        });

        }

}

