package com.nokody.merchant.views.merchant.checkout.main;

import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.data.models.callbacks.RequestPaymentCallBack;
import com.nokody.merchant.data.repositories.TransactionsRepo;

import java.lang.ref.WeakReference;

public class CheckoutPresenter implements CheckoutContract.Presenter {

    private TransactionsRepo transactionsRepo;
    private WeakReference<CheckoutContract.View> mViewReference;

    CheckoutPresenter() {
        transactionsRepo = TransactionsRepo.getInstance();
    }

    @Override
    public void checkout(PaymentBody paymentBody) {

        if (mViewReference != null && mViewReference.get() != null) {

            if (!mViewReference.get().hasConnection()) {
                mViewReference.get().showNotConnected();
                return;
            }

            mViewReference.get().clearError();
            mViewReference.get().showLoading(true);

            transactionsRepo.checkout(paymentBody, new RequestPaymentCallBack() {
                @Override
                public void onSuccess() {
                    if (mViewReference != null && mViewReference.get() != null) {
                        mViewReference.get().showLoading(false);
                        mViewReference.get().showTransactionSuccess();
                    }
                }

                @Override
                public void onFailure(String error) {
                    if (mViewReference != null && mViewReference.get() != null) {
                        mViewReference.get().showLoading(false);
                        mViewReference.get().showError(true, error);
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
    public void attachView(CheckoutContract.View view) {
        mViewReference = new WeakReference<>(view);
    }
}
