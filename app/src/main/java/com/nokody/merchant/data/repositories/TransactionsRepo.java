package com.nokody.merchant.data.repositories;

import com.nokody.merchant.R;
import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.data.models.PaymentResponse;
import com.nokody.merchant.data.models.Transaction;
import com.nokody.merchant.data.models.callbacks.HistoryCallBack;
import com.nokody.merchant.data.models.callbacks.RequestPaymentCallBack;
import com.nokody.merchant.data.rest.ServiceGenerator;
import com.nokody.merchant.data.rest.WebServices;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsRepo {

    private static final String TAG = TransactionsRepo.class.getSimpleName();
    WebServices apiEndPointInterface;


    private static TransactionsRepo instance;

    private TransactionsRepo() {
        apiEndPointInterface = ServiceGenerator.getEndPointInterface();
    }

    public static TransactionsRepo getInstance() {
        if (instance == null) {
            instance = new TransactionsRepo();
        }
        return instance;
    }

    public void requestPayment(String userId, Double amount, RequestPaymentCallBack requestPaymentCallBack) {

        apiEndPointInterface.requestPayment(userId, amount)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null && response.isSuccessful()) {
                            requestPaymentCallBack.onSuccess(response.body());
                        } else {
                            try {
                                requestPaymentCallBack.onFailure(response.errorBody().string());
                            } catch (IOException e) {
                                requestPaymentCallBack.onFailure(response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        requestPaymentCallBack.onFailure(R.string.req_payment_error);
                    }
                });
    }

    public void checkout(PaymentBody paymentBody, RequestPaymentCallBack requestPaymentCallBack) {

        apiEndPointInterface.checkout(paymentBody)
                .enqueue(new Callback<PaymentResponse>() {
                    @Override
                    public void onResponse(Call<PaymentResponse> call, Response<PaymentResponse> response) {
                        if (response != null && response.isSuccessful()) {
                            requestPaymentCallBack.onSuccess(null);
                        } else {
                            try {
                                requestPaymentCallBack.onFailure(response.errorBody().string());
                            } catch (IOException e) {
                                requestPaymentCallBack.onFailure(response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentResponse> call, Throwable t) {
                        requestPaymentCallBack.onFailure(R.string.error);
                    }
                });
    }

    public void getHistory(Integer accountId, HistoryCallBack historyCallback) {

        apiEndPointInterface.getTransactionHistory(accountId)
                .enqueue(new Callback<List<Transaction>>() {
                    @Override
                    public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                        if (response != null && response.isSuccessful()) {
                            historyCallback.onSuccess(response.body());
                        } else {
                            historyCallback.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Transaction>> call, Throwable t) {
                        historyCallback.onFailure();
                    }
                });
    }
}

