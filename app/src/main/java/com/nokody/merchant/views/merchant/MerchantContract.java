package com.nokody.merchant.views.merchant;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.Transaction;

import java.util.List;
import java.util.Map;

public class MerchantContract {

    interface View {
        void showLoading(boolean show);
        void showNoData(boolean show, @StringRes int strId);
        void showTransactionsHistory(List<Transaction> transactionsHistory);
        boolean hasConnection();
        void showNotConnected();
    }

    interface Presenter {
        void attachView(MerchantContract.View view);
        void getTransactions(String day);
    }
}
