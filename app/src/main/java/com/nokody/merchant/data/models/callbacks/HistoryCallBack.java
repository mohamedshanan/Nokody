package com.nokody.merchant.data.models.callbacks;

import com.nokody.merchant.data.models.Transaction;

import java.util.List;

public interface HistoryCallBack {
    void onSuccess(List<Transaction> transactions);
    void onFailure();
}
