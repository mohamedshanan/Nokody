package com.nokody.merchant.views.merchant.main;

import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.callbacks.RequestPaymentCallBack;
import com.nokody.merchant.data.repositories.TransactionsRepo;

import java.lang.ref.WeakReference;

public class MerchantPresenter implements MerchantContract.Presenter {

    private TransactionsRepo transactionsRepo;
    private WeakReference<MerchantContract.View> mViewReference;

    MerchantPresenter() {
        transactionsRepo = TransactionsRepo.getInstance();
    }

    @Override
    public void validate(String userId, Double amount) {
        if (mViewReference != null && mViewReference.get() != null) {

            if (!mViewReference.get().hasConnection()) {
                mViewReference.get().showNotConnected();
                return;
            }

            mViewReference.get().clearError();
            mViewReference.get().showLoading(true);

            transactionsRepo.requestPayment(userId, amount, new RequestPaymentCallBack() {
                @Override
                public void onSuccess(String pinCode) {
                    if (mViewReference != null && mViewReference.get() != null) {
                        mViewReference.get().showValidationSuccess();
                        mViewReference.get().showLoading(false);
                    }
                }

                @Override
                public void onFailure(String error) {
                    if (mViewReference != null && mViewReference.get() != null) {
                        mViewReference.get().showError(true, error);
                        mViewReference.get().showLoading(false);
                    }
                }

                @Override
                public void onFailure(@StringRes int error) {
                    if (mViewReference != null && mViewReference.get() != null) {
                        mViewReference.get().showLoading(false);
                        mViewReference.get().showError(true, error);
                    }
                }
            });

        }
    }

    @Override
    public void attachView(MerchantContract.View view) {
        mViewReference = new WeakReference<>(view);
    }
}
