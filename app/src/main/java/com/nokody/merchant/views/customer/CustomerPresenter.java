package com.nokody.merchant.views.customer;

import android.text.TextUtils;

import com.nokody.merchant.R;
import com.nokody.merchant.data.repositories.AuthRepo;

import java.lang.ref.WeakReference;

public class CustomerPresenter implements CustomerContract.Presenter {

    private AuthRepo authRepo;
    private WeakReference<CustomerContract.View> mViewReference;

    CustomerPresenter(){
        authRepo = AuthRepo.getInstance();
    }

    @Override
    public boolean validateInput(String username, String password) {
        mViewReference.get().clearErrors();

        boolean cancel = false;

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mViewReference.get().showEmailError(true, R.string.error_field_required);
            cancel = true;
        } else if (username.length() < 8) {
            mViewReference.get().showEmailError(true, R.string.error_invalid_id);
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            mViewReference.get().showPasswordError(true, R.string.error_invalid_password);
            cancel = true;
        }

        if (cancel) {
            // There was an error; return false.
            return false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            return true;
        }
    }

    @Override
    public void login(String username, String password) {
        if (mViewReference != null && mViewReference.get() != null
                && validateInput(username, password)){

            if (!mViewReference.get().hasConnection()){
                mViewReference.get().showNotConnected();
                return;
            }

            mViewReference.get().showLoading(true);

        }
    }

    @Override
    public void attachView(CustomerContract.View view) {
            mViewReference = new WeakReference<>(view);
    }
}
