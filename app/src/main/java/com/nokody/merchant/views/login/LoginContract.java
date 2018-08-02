package com.nokody.merchant.views.login;


import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.LoginResponse;

public class LoginContract {

    interface View {
        void showLoading(boolean show);
        void showEmailError(boolean show, @StringRes int strId);
        void showPasswordError(boolean show, @StringRes int strId);
        void showInvalidCredentials(boolean show);
        void clearErrors();
        void goToMerchant(LoginResponse loginResponse);
        void goToCustomer(LoginResponse loginResponse);
        boolean hasConnection();
        void showNotConnected();
    }

    interface Presenter {
        void attachView(LoginContract.View view);
        boolean validateInput(String username, String password);
        void login(String username, String password);
    }
}
