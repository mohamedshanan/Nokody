package com.nokody.merchant.views.transfer.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;
import com.nokody.merchant.views.merchant.checkout.SuccessActivity;
import com.nokody.merchant.views.merchant.checkout.main.CheckoutActivity;
import com.nokody.merchant.views.reader.ReaderActivity;

import butterknife.BindView;

import static com.nokody.merchant.utils.Constants.ENCODED_TEXT;
import static com.nokody.merchant.utils.Constants.QR_READER_CODE;

public class TransferActivity extends BaseActivity implements TransferContract.View {

    @Nullable
    @BindView(R.id.etAmount)
    EditText etAmount;
    @Nullable
    @BindView(R.id.etPassport)
    EditText etPassport;

    @Nullable
    @BindView(R.id.tvValidateError)
    TextView tvValidateError;
    @Nullable
    @BindView(R.id.validateBtn)
    Button validateBtn;

    private TransferContract.Presenter presenter;
    private String myId;
    private String recipientId;
    private Double amount;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, String userPassport) {
        Intent intent = new Intent(context, TransferActivity.class);
        intent.putExtra(Constants.USER_PASSPORT, userPassport);
        return intent;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_merchant_main;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.transfer;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {
        presenter = new TransferPresenter();
        presenter.attachView(this);
        myId = getIntent().getStringExtra(Constants.USER_PASSPORT);

        etPassport.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etPassport.getRight() - etPassport.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    startActivityForResult(new Intent(this, ReaderActivity.class)
                            , QR_READER_CODE);
                    return true;
                }
            }
            return false;
        });

        validateBtn.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etAmount.getText().toString())) {
                etAmount.setError(getString(R.string.error_field_required));
                return;
            }

            if (TextUtils.isEmpty(etPassport.getText().toString())) {
                etPassport.setError(getString(R.string.error_field_required));
                return;
            }

            recipientId = etPassport.getText().toString();
            amount = Double.valueOf(etAmount.getText().toString());

            presenter.validate(myId, amount);
        });

    }

    @Override
    public void showLoading(boolean show) {
        setLoading(show);
    }

    @Override
    public void showError(boolean show, int strId) {
        if (show) {
            tvValidateError.setText(getString(strId));
            tvValidateError.setVisibility(View.VISIBLE);
        } else {
            tvValidateError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(boolean show, String errorMessage) {
        if (show) {
            tvValidateError.setText(errorMessage);
            tvValidateError.setVisibility(View.VISIBLE);
        } else {
            tvValidateError.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearError() {
        tvValidateError.setVisibility(View.GONE);
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
    public void showValidationSuccess(String pinCode) {

        if (recipientId != null && myId != null && amount != null) {
            PaymentBody paymentBody = new PaymentBody(pinCode, myId, recipientId, amount);
            presenter.checkout(paymentBody);
        }
    }

    @Override
    public void showTransactionSuccess() {
        startActivity(new Intent(this, SuccessActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_READER_CODE && resultCode == RESULT_OK && data != null) {

            if (data.getStringExtra(ENCODED_TEXT) != null) {
                String encodedText = data.getStringExtra(ENCODED_TEXT);

                if (!TextUtils.isEmpty(encodedText)) {
                    etPassport.setText(encodedText);
                }
            }
        }
    }
}
