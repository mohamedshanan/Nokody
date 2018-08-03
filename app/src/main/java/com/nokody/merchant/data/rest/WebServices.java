package com.nokody.merchant.data.rest;

import com.nokody.merchant.data.models.LoginData;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.PaymentBody;
import com.nokody.merchant.data.models.PaymentResponse;
import com.nokody.merchant.data.models.TokenUpdateBody;
import com.nokody.merchant.data.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServices {

    @POST("user/authenticate")
    Call<LoginResponse> login(@Body LoginData loginData);

    @POST("user/updateDeviceId")
    Call<LoginResponse> updateFCMToken(@Body TokenUpdateBody tokenUpdateBody);

    @GET("account/validate/{userId}/{amount}")
    Call<String> requestPayment(@Path("userId") String userId, @Path("amount") Double amount);

    @POST("transaction")
    Call<PaymentResponse> checkout(@Body PaymentBody paymentBody);

    @GET("transaction/history/{accountId}")
    Call<List<Transaction>> getTransactionHistory(@Path("accountId") Integer id);


}
