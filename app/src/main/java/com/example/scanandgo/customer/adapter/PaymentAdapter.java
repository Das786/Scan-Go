package com.example.scanandgo.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanandgo.R;
import com.example.scanandgo.customer.models.PaymentModel;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    Context context;
    ArrayList<PaymentModel> paymentModel;

    public PaymentAdapter(Context context, ArrayList<PaymentModel> paymentModel) {
        this.context = context;
        this.paymentModel = paymentModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        PaymentModel model = paymentModel.get(position);
        holder.paymentDate.setText(model.getCurrentDate());
        holder.paymentOrderId.setText(model.getTransactionId());
        holder.paymentAmount.setText(model.getAmount());
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return paymentModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView paymentDate, paymentOrderId,paymentAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paymentDate = itemView.findViewById(R.id.paymentdate);
            paymentOrderId = itemView.findViewById(R.id.orderId);
            paymentAmount= itemView.findViewById(R.id.paymentAmount);
        }
    }
}
