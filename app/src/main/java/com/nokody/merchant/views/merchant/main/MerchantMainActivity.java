package com.nokody.merchant.views.merchant.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
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
    @BindView(R.id.tvValidateError)
    TextView tvValidateError;
    @Nullable
    @BindView(R.id.validateBtn)
    Button validateBtn;

    private MerchantContract.Presenter presenter;
    private String customerId;
    private Double amount;
    private String myPassport;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, String userPassport) {
        Intent intent = new Intent(context, MerchantMainActivity.class);
        intent.putExtra(Constants.USER_PASSPORT, userPassport);
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

        myPassport = getIntent().getStringExtra(Constants.USER_PASSPORT);

        etPassport.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if(event.getAction() == MotionEvent.ACTION_UP) {
                if(event.getRawX() >= (etPassport.getRight() - etPassport.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
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

        if (customerId != null && myPassport != null && amount != null) {
            mNavigator.checkout(customerId, myPassport, amount);
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
