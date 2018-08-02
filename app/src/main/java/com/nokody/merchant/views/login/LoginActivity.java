package com.nokody.merchant.views.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.utils.Utilities;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements LoginContract.View {

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

    private LoginContract.Presenter presenter;

    @Nullable
    public static Intent buildIntent(@NonNull Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_login;
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
        presenter = new LoginPresenter();
        presenter.attachView(this);

        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnLogin.performClick();
                return true;
            }
            return false;
        });

        btnLogin.setOnClickListener(v -> {
            presenter.login(etId.getText().toString(), etPassword.getText().toString());
        });
    }

    @Override
    public void showLoading(boolean show) {
        setLoading(show);
    }

    public void showEmailError(boolean show, @StringRes int strId) {
        if (show) {
            etId.setError(getString(strId));
            etId.setVisibility(View.VISIBLE);
            etId.requestFocus();
        } else {
            etId.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPasswordError(boolean show, @StringRes int strId) {
        if (show){
            etPassword.setError(getString(strId));
            etPassword.setVisibility(View.VISIBLE);
            etId.requestFocus();
        } else {
            etPassword.setVisibility(View.GONE);
        }

    }

    @Override
    public void showInvalidCredentials(boolean show) {
           if (show){
               tvError.setVisibility(View.VISIBLE);
           } else {
               tvError.setVisibility(View.GONE);
           }
    }

    @Override
    public void clearErrors() {
        etId.setError(null);
        etPassword.setError(null);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void goToMerchant(LoginResponse loginResponse) {
        if (loginResponse.getPassportNumber() != null){
            mNavigator.merchantHome(loginResponse.getPassportNumber());
        } else if (loginResponse.getBraceletNumber() != null){
            mNavigator.merchantHome(loginResponse.getBraceletNumber());
        }

        finish();
    }

    @Override
    public void goToCustomer(LoginResponse loginResponse) {
        mNavigator.customerHome(loginResponse.getBalance());
        finish();
    }

    @Override
    public boolean hasConnection(){
        return Utilities.getNetworkState(this);
    }

    @Override
    public void showNotConnected(){
        showNoConnection();
    }
}
