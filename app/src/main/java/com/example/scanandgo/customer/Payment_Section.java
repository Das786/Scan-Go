package com.example.scanandgo.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.parser.IntegerParser;
import  com.example.scanandgo.R;
import com.example.scanandgo.customer.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Payment_Section extends AppCompatActivity implements PaymentResultWithDataListener {

    Button buttonpay,paymentback;
    TextView CartPrice;
    CheckBox codpaymentradio, razorpaymentradio;
    FirebaseFirestore firestore;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_section);

        buttonpay = findViewById(R.id.buttonpay);
        paymentback = findViewById(R.id.paymentback);
        codpaymentradio = findViewById(R.id.codpayment);
        razorpaymentradio = findViewById(R.id.codpayment2);

        CartPrice = findViewById(R.id.CartPrice);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();









        paymentback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



       codpaymentradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (buttonView.isChecked()){
                   razorpaymentradio.setChecked(false);
               }
           }
       });

       razorpaymentradio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(buttonView.isChecked()){
                   codpaymentradio.setChecked(false);

               }
           }
       });




        buttonpay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!codpaymentradio.isChecked() && !razorpaymentradio.isChecked()){
                    Toast.makeText(getApplicationContext(),"Select the payment gateway",Toast.LENGTH_SHORT).show();
                }if(codpaymentradio.isChecked()){
                    Intent intent = new Intent(getApplicationContext(),PaymentSuccess.class);
                    startActivity(intent);
                    finish();
                }else{
                    makepayment();
                }

            }



            public void makepayment() {

                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_inVptzujZl1kGS");
                checkout.setImage(R.drawable.logo);

                final Activity activity = Payment_Section.this;
                Intent intent = getIntent();
                String s1 = intent.getStringExtra("Amount");
                Integer Amount = Integer.parseInt(s1);
                Integer Amount1 = Amount * 100;
                String s = String.valueOf(Amount1);
                Toast.makeText(getApplication(),"Amount"+s,Toast.LENGTH_SHORT).show();
                CartPrice.setText(s);





                try {
                    JSONObject options = new JSONObject();

                    options.put("name", "Merchant Name");
                    options.put("description", "Reference No. #123456");
                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//                    options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
                    options.put("theme.color", "#3399cc");
                    options.put("currency", "INR");
                    options.put("amount", s);//pass amount in currency subunits
                    options.put("prefill.email", "gaurav.kumar@example.com");
                    options.put("prefill.contact","9988776655");
                    JSONObject retryObj = new JSONObject();
                    retryObj.put("enabled", true);
                    retryObj.put("max_count", 4);
                    options.put("retry", retryObj);

                    checkout.open(activity, options);


                } catch(Exception e) {
                    Log.e("TAG", "Error in starting Razorpay Checkout", e);
                }

            }
        });


    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        try{


            String saveCurrentTime, SaveCurrentDate;

            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentTime = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            SaveCurrentDate = currentTime.format(calForDate.getTime());

            final HashMap<String,Object> paymentMap = new HashMap<>();

            paymentMap.put("transactionId",s);
//            paymentMap.put("OrderId");
            paymentMap.put("currentTime",saveCurrentTime);
            paymentMap.put("currentDate",SaveCurrentDate);
            paymentMap.put("paymentStatus","Success");
            paymentMap.put("Amount","RS 10000");

            firestore.getInstance().collection("PaymentDetails").document(auth.getCurrentUser().getUid())
                    .collection("User").add(paymentMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(getApplicationContext(), "Payment is Successful: ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PaymentSuccess.class));
                    finish();

                }
            });


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            String saveCurrentTime, SaveCurrentDate;

            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentTime = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            SaveCurrentDate = currentTime.format(calForDate.getTime());

            final HashMap<String,Object> paymentMap = new HashMap<>();

            paymentMap.put("paymentStatus", "Failed");
            paymentMap.put("currentTime",saveCurrentTime);
            paymentMap.put("currentDate",SaveCurrentDate);
            paymentMap.put("Amount","RS 500");



            Log.e("TAG","payment Failed:"+paymentData);

            firestore.getInstance().collection("PaymentDetails").document(auth.getCurrentUser().getUid())
                    .collection("User").add(paymentMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(getApplicationContext(), "Payment is UnSuccessful: ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PaymentSuccess.class));
                    finish();

                }
            });
            Toast.makeText(getApplicationContext(), "Payment is UnSuccessfully: "+s, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),PaymentFailed.class));
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
        }

    }
}