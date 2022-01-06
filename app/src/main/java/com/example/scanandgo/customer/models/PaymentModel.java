package com.example.scanandgo.customer.models;

public class PaymentModel {
    String currentDate;
    String transactionId;
    String Amount;

    public PaymentModel() {
    }

    public PaymentModel(String currentDate, String transactionId, String amount) {
        this.currentDate = currentDate;
        this.transactionId = transactionId;
        Amount = amount;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}

