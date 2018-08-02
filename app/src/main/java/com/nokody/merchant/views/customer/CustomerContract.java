package com.nokody.merchant.views.customer;


import android.support.annotation.StringRes;

public class CustomerContract {

    interface View {
        void showLoading(boolean show);
        void showEmailError(boolean show, @StringRes int strId);
        void showPasswordError(boolean show, @StringRes int strId);
        void showInvalidCredentials(boolean show);
        void clearErrors();
        void goToMerchant();
        void goToCustomer();
        boolean hasConnection();
        void showNotConnected();
    }

    interface Presenter {
        void attachView(CustomerContract.View view);
        boolean validateInput(String username, String password);
        void login(String username, String password);
    }
}
