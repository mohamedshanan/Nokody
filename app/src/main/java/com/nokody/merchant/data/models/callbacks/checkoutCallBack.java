package com.nokody.merchant.data.models.callbacks;

import android.support.annotation.StringRes;

public interface checkoutCallBack {
    void onSuccess();
    void onFailure(String message);
    void onFailure(@StringRes int message);
}
