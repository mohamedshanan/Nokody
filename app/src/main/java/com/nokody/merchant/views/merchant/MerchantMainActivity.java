package com.nokody.merchant.views.merchant;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nokody.merchant.R;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.Transaction;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;

public class MerchantMainActivity extends AppCompatActivity implements MerchantContract.View {


    private LoginResponse loginResponse;
    private MerchantContract.Presenter presenter;

    @Nullable
    public static Intent buildIntent(@NonNull Context context, LoginResponse loginResponse) {
        Intent intent = new Intent(context, MerchantMainActivity.class);
//        intent.putExtra(Constants.USER_TYPE_SELLER, Parcels.wrap(loginResponse));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);
        presenter = new MerchantPresenter();
        presenter.attachView(this);
        presenter.getTransactions("today");

    }

//    @Override
//    protected int getActivityView() {
//        return R.layout.activity_merchant_main;
//    }
//
//    @Override
//    protected int getToolbarTitleResource() {
//        return R.string.home;
//    }
//
//    @Override
//    protected boolean isHomeAsUpEnabled() {
//        return true;
//    }
//
//    @Override
//    protected void afterInflation(Bundle savedInstance) {
//
//    }

    @Override
    public void showLoading(boolean show) {
//        setLoading(show);
    }

    @Override
    public void showNoData(boolean show, int strId) {
//        showNoData(show, R.string.no_data);
    }

    @Override
    public void showTransactionsHistory(List<Transaction> transactionsHistory) {

    }

    @Override
    public boolean hasConnection() {
        return Utilities.getNetworkState(this);
    }

    @Override
    public void showNotConnected() {
//        showNoConnection();
    }
}
