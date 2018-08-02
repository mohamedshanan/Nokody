package com.nokody.merchant.views.login;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.nokody.merchant.R;
import com.nokody.merchant.data.models.LoginData;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.TokenUpdateBody;
import com.nokody.merchant.data.models.callbacks.LoginCallBack;
import com.nokody.merchant.data.repositories.AuthRepo;

import java.lang.ref.WeakReference;

public class LoginPresenter implements LoginContract.Presenter {

    private AuthRepo authRepo;
    private WeakReference<LoginContract.View> mViewReference;

    LoginPresenter() {
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
        }
//        else if (username.length()) {
//            mViewReference.get().showEmailError(true, R.string.error_invalid_id);
//            cancel = true;
//        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
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
                && validateInput(username, password)) {

            if (!mViewReference.get().hasConnection()) {
                mViewReference.get().showNotConnected();
                return;
            }

            mViewReference.get().showLoading(true);

            LoginData loginData = new LoginData(username, password);

            authRepo.login(loginData, new LoginCallBack() {
                @Override
                public void onSuccess(LoginResponse loginResponse) {
                    if (mViewReference.get() != null) {
                        mViewReference.get().showLoading(false);
                        if (loginResponse != null && loginResponse.getUser() != null) {

                            updateFMCToken(loginResponse.getId());

                            if (loginResponse.getUser().getUserTypeId() == 1) {
                                mViewReference.get().goToCustomer(loginResponse);
                            } else {
                                mViewReference.get().goToMerchant(loginResponse);
                            }
                        } else {
                            onFailure();
                        }
                    }
                }

                @Override
                public void onFailure() {
                    if (mViewReference.get() != null) {
                        mViewReference.get().showInvalidCredentials(true);
                        mViewReference.get().showLoading(false);
                    }
                }
            });
        }
    }

    private void updateFMCToken(Integer id) {

        if (!mViewReference.get().hasConnection()) {
            mViewReference.get().showNotConnected();
            return;
        }

        TokenUpdateBody tokenUpdateBody = new TokenUpdateBody(id,
                FirebaseInstanceId.getInstance().getToken());

        authRepo.updateToken(tokenUpdateBody, new LoginCallBack() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void attachView(LoginContract.View view) {
        mViewReference = new WeakReference<>(view);
    }
}
