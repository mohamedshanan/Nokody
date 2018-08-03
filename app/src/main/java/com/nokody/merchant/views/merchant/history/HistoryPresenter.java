package com.nokody.merchant.views.merchant.history;

import android.support.annotation.StringRes;

import com.nokody.merchant.R;
import com.nokody.merchant.data.models.Transaction;
import com.nokody.merchant.data.models.callbacks.HistoryCallBack;
import com.nokody.merchant.data.models.callbacks.RequestPaymentCallBack;
import com.nokody.merchant.data.repositories.TransactionsRepo;

import java.lang.ref.WeakReference;
import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter {

    private TransactionsRepo transactionsRepo;
    private WeakReference<HistoryContract.View> mViewReference;

    HistoryPresenter() {
        transactionsRepo = TransactionsRepo.getInstance();
    }

    @Override
    public void getTransactions(Integer accountId) {
        if (mViewReference != null && mViewReference.get() != null) {

            if (!mViewReference.get().hasConnection()) {
                mViewReference.get().showNotConnected();
                return;
            }

            mViewReference.get().showLoading(true);

            transactionsRepo.getHistory(accountId, new HistoryCallBack() {

                    @Override
                    public void onSuccess(List<Transaction> transactions) {
                        if (mViewReference != null && mViewReference.get() != null) {
                            mViewReference.get().showLoading(false);
                            mViewReference.get().showTransactionsHistory(transactions);
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (mViewReference != null && mViewReference.get() != null) {
                            mViewReference.get().showLoading(false);
                            mViewReference.get().showNoData();
                        }
                    }
            });
        }
    }


    @Override
    public void attachView(HistoryContract.View view) {
        mViewReference = new WeakReference<>(view);
    }
}
