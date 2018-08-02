package com.nokody.merchant.data.repositories;

import com.nokody.merchant.R;
import com.nokody.merchant.data.models.HistoryResponse;
import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.data.models.PaymentResponse;
import com.nokody.merchant.data.models.callbacks.HistoryCallBack;
import com.nokody.merchant.data.models.callbacks.RequestPaymentCallBack;
import com.nokody.merchant.data.rest.ServiceGenerator;
import com.nokody.merchant.data.rest.WebServices;

import java.io.IOException;

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

    public void getHistory(String day, HistoryCallBack historyCallBack) {

        apiEndPointInterface.getHistory(day)
                .enqueue(new Callback<HistoryResponse>() {
                    @Override
                    public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                        if (response != null && response.isSuccessful()){
                            historyCallBack.onSuccess(response.body().getTransactions());
                        } else {
                            historyCallBack.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryResponse> call, Throwable t) {
                        historyCallBack.onFailure();
                    }
                });
    }

    public void requestPayment(String userId, Double amount , RequestPaymentCallBack requestPaymentCallBack) {

        apiEndPointInterface.requestPayment(userId, amount)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response != null && response.isSuccessful()){
                            requestPaymentCallBack.onSuccess();
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
                        if (response != null && response.isSuccessful()){
                            requestPaymentCallBack.onSuccess();
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

}
