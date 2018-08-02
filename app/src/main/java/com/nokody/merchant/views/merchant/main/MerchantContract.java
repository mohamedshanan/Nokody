package com.nokody.merchant.views.merchant.main;


import android.support.annotation.StringRes;

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
