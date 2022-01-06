package com.example.scanandgo.customer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.TodoGoceryList;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;



    public ListViewAdapter( Context context, ArrayList<String>items) {
        super(context, R.layout.list_row,items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row,null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 +".");

            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position));

            ImageView copy = convertView.findViewById(R.id.copy);
            ImageView remove = convertView.findViewById(R.id.remove);
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoGoceryList.addItems(list.get(position));

                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TodoGoceryList.removeItem(position);
                }
            });
        }

        return  convertView;
    }
}
