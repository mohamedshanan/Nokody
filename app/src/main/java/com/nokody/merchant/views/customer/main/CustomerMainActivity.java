package com.nokody.merchant.views.customer.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.utils.Constants;

import butterknife.BindView;

public class CustomerMainActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.tvBalance)
    TextView tvBalance;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, Double balance) {
        Intent intent = new Intent(context, CustomerMainActivity.class);
//        intent.putExtra(Constants.USER_TYPE_CUSTOMER, Parcels.wrap(loginResponse));
        intent.putExtra(Constants.USER_TYPE_CUSTOMER, balance);
        return intent;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_customer_main;
    }

    @Override
    protected int getToolbarTitleResource() {
        return 0;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {

        if (getIntent() != null && getIntent().hasExtra(Constants.USER_TYPE_CUSTOMER)) {
            Double balance = getIntent().getDoubleExtra(Constants.USER_TYPE_CUSTOMER, 0.0);

            tvBalance.setText(String.valueOf(balance));

        }
    }
}
