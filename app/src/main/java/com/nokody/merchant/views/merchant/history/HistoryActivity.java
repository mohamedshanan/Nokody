package com.nokody.merchant.views.merchant.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nokody.merchant.R;
import com.nokody.merchant.base.BaseActivity;
import com.nokody.merchant.data.models.Transaction;
import com.nokody.merchant.utils.Constants;
import com.nokody.merchant.utils.Utilities;

import java.util.List;

import butterknife.BindView;

public class HistoryActivity extends BaseActivity implements HistoryContract.View {

    @Nullable
    @BindView(R.id.transactionsRV)
    RecyclerView transactionsRV;

    private HistoryContract.Presenter presenter;

    @Nullable
    public static Intent buildIntent(@NonNull Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        return intent;
    }

    @Override
    protected int getActivityView() {
        return R.layout.activity_history;
    }

    @Override
    protected int getToolbarTitleResource() {
        return R.string.history;
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected void afterInflation(Bundle savedInstance) {
        presenter = new HistoryPresenter();
        presenter.attachView(this);
        presenter.getTransactions(Utilities.getIntFromPref(this, Constants.ACCOUNT_ID));
    }

    @Override
    public void showLoading(boolean show) {
        setLoading(show);
    }

    @Override
    public void showNoData() {
        setError(R.string.no_data, R.string.reload, R.drawable.ic_refresh,
                v -> presenter.getTransactions(
                        Utilities.getIntFromPref(this, Constants.ACCOUNT_ID)));
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
    public void showTransactionsHistory(List<Transaction> transactions) {
        transactionsRV.setHasFixedSize(true);
        transactionsRV.setLayoutManager(new LinearLayoutManager(this));
        transactionsRV.setAdapter(new TransactionsHistoryAdapter(this, transactions,
                Utilities.getIntFromPref(this, Constants.ACCOUNT_ID)));

    }
}
