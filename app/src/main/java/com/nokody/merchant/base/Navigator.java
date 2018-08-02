package com.nokody.merchant.base;

import android.content.Context;

import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.views.customer.main.CustomerMainActivity;
import com.nokody.merchant.views.login.LoginActivity;
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

    public void merchantHome(LoginResponse loginResponse) {
        if (mContext != null){
            mContext.startActivity(MerchantMainActivity.buildIntent(mContext, loginResponse));
        }
    }
}
