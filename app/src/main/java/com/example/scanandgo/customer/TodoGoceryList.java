package com.example.scanandgo.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.adapter.ListViewAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class TodoGoceryList extends AppCompatActivity {

    static ListView listView;
   static   ArrayList<String> items;
   static ListViewAdapter adapter;

    EditText input;
    ImageView enter, back;


    public void loadContent(){
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, "list.txt");
        byte[] content = new byte[(int) readFrom.length()];

        FileInputStream stream = null;
        try {
            String s = new String(String.valueOf(readFrom));
            stream.read(content);

            String str = new String(content);

            str = str.substring(1,s.length()-1);
            String spilt[] = s.split(", ");

            items = new ArrayList<>(Arrays.asList(spilt));
            adapter = new ListViewAdapter(this,items);
            listView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
            File path = getApplicationContext().getFilesDir();
            try {
                FileOutputStream writer = new FileOutputStream(new File(path,"list.txt"));
                writer.write(items.toString().getBytes());
                writer.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);



        items = new ArrayList<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Orange");
        items.add("Strawberry");
        items.add("Kiwi");
        items.add("Mango");

        listView  = findViewById(R.id.listview);
        input = findViewById(R.id.input);
        enter = findViewById(R.id.add);
        back = findViewById(R.id.back);


       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String name = items.get(position);
               makeToast(name);

           }

       });

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               makeToast("Long press : "+items.get(position));
               removeItem(position);
               return false;
           }
       });

       enter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String text = input.getText().toString();
               if (text == null || text.length() ==0 ){
                   makeToast("Enter an item ");
               }else {

               }
           }
       });

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });

        adapter = new ListViewAdapter(getApplicationContext(),items);
        listView.setAdapter(adapter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (text == null || text.length() ==0 ){
                    makeToast("Enter the Item ");
                }
                else {
                    addItems(text);
                    input.setText("");
                    makeToast("Added: "+ text);
                }
            }


        });





    }

    public static void  removeItem(int remove){
        items.remove(remove);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static void addItems(String item){
        items.add(item);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    Toast t;
    private void  makeToast(String s){
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();
    }
}