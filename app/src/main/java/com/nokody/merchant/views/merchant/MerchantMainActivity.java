package com.nokody.merchant.views.merchant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.Transaction;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MerchantMainActivity extends BaseActivity implements MerchantContract.View {

    @Nullable
    @BindView(R.id.etId)
    EditText etId;
    @Nullable
    @BindView(R.id.etPassword)
    EditText etPassword;
    @Nullable
    @BindView(R.id.tvError)
    TextView tvError;
    @Nullable
    @BindView(R.id.btnLogin)
    Button btnLogin;
    
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
    }

    @Override
    public void showLoading(boolean show) {
        setLoading(show);
    }

    @Override
    public void showError(boolean show, int strId) {
        if (show) {
            tvError.setText(getString(strId));
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(boolean show, String errorMessage) {
        if (show) {
            tvError.setText(errorMessage);
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearError() {
        tvError.setVisibility(View.GONE);
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

    }
}
