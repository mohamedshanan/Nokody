package com.nokody.merchant.data.models.callbacks;

import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.Transaction;

import java.util.List;

public interface RequestPaymentCallBack {
    void onSuccess();
    void onFailure(String message);
    void onFailure(@StringRes int message);
}
