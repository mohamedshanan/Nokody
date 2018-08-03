package com.nokody.merchant.views.merchant.history;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.Transaction;

import java.util.List;

public class HistoryContract {

    interface View {
        void showLoading(boolean show);
        void showNoData();
        boolean hasConnection();
        void showNotConnected();
        void showTransactionsHistory(List<Transaction> transactions);
    }

    interface Presenter {
        void attachView(HistoryContract.View view);
        void getTransactions(Integer accountId);
    }
}
