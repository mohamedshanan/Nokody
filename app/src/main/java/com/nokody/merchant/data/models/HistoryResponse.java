package com.nokody.merchant.data.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class HistoryResponse {
    private List<Transaction> transactions;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
