package com.nokody.merchant.base;

import android.content.Context;

import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.views.customer.main.CustomerMainActivity;
import com.nokody.merchant.views.login.LoginActivity;
import com.nokody.merchant.views.merchant.checkout.main.CheckoutActivity;
import com.nokody.merchant.views.merchant.history.HistoryActivity;
import com.nokody.merchant.views.merchant.main.MerchantMainActivity;

public class Navigator {

    private Context mContext;

    public void setContext(Context context){
        mContext = context;
    }

    public void login() {
        if (mContext != null){
            mContext.startActivity(LoginActivity.buildIntent(mContext));
        }
    }

    public void customerHome(Double balance) {
        if (mContext != null){
            mContext.startActivity(CustomerMainActivity.buildIntent(mContext, balance));
        }
    }

    public void merchantHome(String userId) {
        if (mContext != null){
            mContext.startActivity(MerchantMainActivity.buildIntent(mContext, userId));
        }
    }

    public void checkout(String fromId, String toId, Double amount) {
        if (mContext != null){
            mContext.startActivity(CheckoutActivity.buildIntent(mContext, fromId, toId, amount));
        }
    }

    public void history() {
        if (mContext != null){
            mContext.startActivity(HistoryActivity.buildIntent(mContext));
        }
    }
}
