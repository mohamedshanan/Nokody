package com.nokody.merchant.views.merchant.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;
import com.nokody.merchant.views.reader.ReaderActivity;

import butterknife.BindView;

import static com.nokody.merchant.utils.Constants.ENCODED_TEXT;
import static com.nokody.merchant.utils.Constants.QR_READER_CODE;

public class MerchantMainActivity extends BaseActivity implements MerchantContract.View {

    @Nullable
    @BindView(R.id.etAmount)
    EditText etAmount;
    @Nullable
    @BindView(R.id.etPassport)
    EditText etPassport;
    @Nullable
    @BindView(R.id.scan)
    ImageView scan;
    @Nullable
    @BindView(R.id.tvValidateError)
    TextView tvValidateError;
    @Nullable
    @BindView(R.id.validateBtn)
    Button validateBtn;

    private LoginResponse loginResponse;
    private MerchantContract.Presenter presenter;
    private String customerId;
    private Double amount;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, String userPassport) {
        Intent intent = new Intent(context, MerchantMainActivity.class);
        intent.putExtra(Constants.USER_TYPE_SELLER, userPassport);
        return intent;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_merchant_main;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.home;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {
        presenter = new MerchantPresenter();
        presenter.attachView(this);

        scan.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, ReaderActivity.class)
                    , QR_READER_CODE);
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

            customerId = etPassport.getText().toString();
            amount = Double.valueOf(etAmount.getText().toString());

            presenter.validate(customerId, amount);
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
    public void showValidationSuccess() {

        String myId = getIntent().getStringExtra(Constants.USER_TYPE_SELLER);

        if (customerId != null && myId != null && amount != null) {
            mNavigator.checkout(customerId, myId, amount);
        }
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
