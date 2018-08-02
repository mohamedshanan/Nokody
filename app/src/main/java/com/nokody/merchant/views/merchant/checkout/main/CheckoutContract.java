package com.nokody.merchant.views.merchant.checkout.main;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.PaymentBody;

public class CheckoutContract {

    interface View {
        void showLoading(boolean show);
        void showError(boolean show, @StringRes int strId);
        void showError(boolean show, String errorMessage);
        void clearError();
        boolean hasConnection();
        void showNotConnected();
        void showTransactionSuccess();
    }

    interface Presenter {
        void attachView(CheckoutContract.View view);
        void checkout(PaymentBody paymentBody);
    }
}
