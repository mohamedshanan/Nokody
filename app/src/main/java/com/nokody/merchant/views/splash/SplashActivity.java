package com.nokody.merchant.views.splash;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getActivityView() {
        return R.layout.activity_splash;
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNavigator.login();
                finish();

            }
        }, 600);
    }
}
