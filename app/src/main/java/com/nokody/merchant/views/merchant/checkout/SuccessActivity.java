package com.nokody.merchant.views.merchant.checkout;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;

public class SuccessActivity extends BaseActivity {

    @Override
    protected int getActivityView() {
        return R.layout.activity_success;
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
        Handler handler = new Handler();

        handler.postDelayed((Runnable) () -> {
            finish();
        }, 2000);
    }
}
