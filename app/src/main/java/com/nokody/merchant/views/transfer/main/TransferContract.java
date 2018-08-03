package com.nokody.merchant.views.transfer.main;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.PaymentBody;

public class TransferContract {

    interface View {
        void showLoading(boolean show);
        void showError(boolean show, @StringRes int strId);
        void showError(boolean show, String errorMessage);
        void clearError();
        boolean hasConnection();
        void showNotConnected();
        void showValidationSuccess(String picCode);
        void showTransactionSuccess();
    }

    interface Presenter {
        void attachView(TransferContract.View view);
        void validate(String userId, Double amount);
        void checkout(PaymentBody paymentBody);
    }
}
