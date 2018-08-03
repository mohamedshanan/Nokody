package com.nokody.merchant.views.customer.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.User;
import com.nokody.merchant.utils.Constants;

import butterknife.BindView;

public class CustomerMainActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.tvBalance)
    TextView tvBalance;

    private String myPassport;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, String userPassport, Double balance) {
        Intent intent = new Intent(context, CustomerMainActivity.class);
        intent.putExtra(Constants.USER_PASSPORT, userPassport);
        intent.putExtra(Constants.USER_TYPE_CUSTOMER, balance);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_history){
            mNavigator.history();
        } else if (item.getItemId() == R.id.action_transfer){
            mNavigator.transfer(myPassport);
        }
        return true;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_customer_main;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.home;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {

        myPassport = getIntent().getStringExtra(Constants.USER_PASSPORT);

        if (getIntent() != null && getIntent().hasExtra(Constants.USER_TYPE_CUSTOMER)) {
            Double balance = getIntent().getDoubleExtra(Constants.USER_TYPE_CUSTOMER, 0.0);

            tvBalance.setText(String.valueOf(balance));

        }
    }
}
