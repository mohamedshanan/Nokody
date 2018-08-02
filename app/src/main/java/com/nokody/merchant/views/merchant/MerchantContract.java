package com.nokody.merchant.views.merchant;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.Transaction;

import java.util.List;

public class MerchantContract {

    interface View {
        void showLoading(boolean show);
        void showError(boolean show, @StringRes int strId);
        void showError(boolean show, String errorMessage);
        void clearError();
        boolean hasConnection();
        void showNotConnected();
        void showValidationSuccess();
    }

    interface Presenter {
        void attachView(MerchantContract.View view);
        void getTransactions(String day);
        void validate(String userId, Double amount);
    }
}
