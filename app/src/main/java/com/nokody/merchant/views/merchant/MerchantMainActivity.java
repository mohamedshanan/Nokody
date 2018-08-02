package com.nokody.merchant.views.merchant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.utils.Utilities;

import butterknife.BindView;

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

    @Nullable
    public static Intent buildIntent(@NonNull Context context, LoginResponse loginResponse) {
        Intent intent = new Intent(context, MerchantMainActivity.class);
//        intent.putExtra(Constants.USER_TYPE_SELLER, Parcels.wrap(loginResponse));
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
        validateBtn.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etAmount.getText().toString())){
                etAmount.setError(getString(R.string.error_field_required));
                return;
            }

            if (TextUtils.isEmpty(etPassport.getText().toString())){
                etPassport.setError(getString(R.string.error_field_required));
                return;
            }

            presenter.validate(etPassport.getText().toString(),
                    Double.valueOf(etAmount.getText().toString()));
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
        Toast.makeText(this, "Validation success", Toast.LENGTH_SHORT).show();
    }
}
