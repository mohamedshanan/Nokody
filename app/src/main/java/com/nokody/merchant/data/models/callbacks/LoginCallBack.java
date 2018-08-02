package com.nokody.merchant.data.models.callbacks;

import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.LoginResponse;

public interface LoginCallBack {
    void onSuccess(LoginResponse loginResponse);
    void onFailure();
}
