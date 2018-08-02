package com.nokody.merchant.views.merchant.checkout.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.infideap.blockedittext.BlockEditText;
import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;

import butterknife.BindView;

public class CheckoutActivity extends BaseActivity implements CheckoutContract.View {

    @Nullable
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @Nullable
    @BindView(R.id.binEt)
    BlockEditText binEt;
    @Nullable
    @BindView(R.id.pinError)
    TextView pinError;
    @Nullable
    @BindView(R.id.btnPay)
    Button btnPay;

    private CheckoutContract.Presenter presenter;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, String fromId, String toId, Double amount) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(Constants.FROM_ID, fromId);
        intent.putExtra(Constants.TO_ID, toId);
        intent.putExtra(Constants.AMOUNT, amount);
        return intent;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_checkout;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.payment;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {

        presenter = new CheckoutPresenter();
        presenter.attachView(this);
        String fromId = getIntent().getStringExtra(Constants.FROM_ID);
        String toId = getIntent().getStringExtra(Constants.TO_ID);
        Double amount = getIntent().getDoubleExtra(Constants.AMOUNT, 0.0);
        tvAmount.setText(String.valueOf(amount));

        btnPay.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binEt.getText().toString())) {
                pinError.setText(getString(R.string.invalid_pin_code));
                pinError.setVisibility(View.VISIBLE);
                return;
            } else {

                PaymentBody paymentBody = new PaymentBody(binEt.getText().toString(), fromId, toId, amount);
                pinError.setVisibility(View.GONE);
                presenter.checkout(paymentBody);
            }
        });

    }

    @Override
    public void showLoading(boolean show) {
        setLoading(show);
    }

    @Override
    public void showError(boolean show, int strId) {
        if (show) {
            pinError.setText(getString(strId));
            pinError.setVisibility(View.VISIBLE);
        } else {
            pinError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(boolean show, String errorMessage) {
        if (show) {
            pinError.setText(errorMessage);
            pinError.setVisibility(View.VISIBLE);
        } else {
            pinError.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearError() {
        pinError.setVisibility(View.GONE);
    }

    @Override
    public boolean hasConnection() {
        return Utilities.getNetworkState(this);
    }

    @Override
    public void showNotConnected() {
        showNoConnection();
    }

    @Override
    public void showTransactionSuccess() {
        Toast.makeText(this, "Transaction success", Toast.LENGTH_SHORT).show();
    }
}
