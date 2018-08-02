package com.nokody.merchant.data.rest;

import android.support.annotation.StringRes;

import com.nokody.merchant.data.models.HistoryResponse;
import com.nokody.merchant.data.models.LoginData;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.callbacks.HistoryCallBack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServices {

    @POST("user/authenticate")
    Call<LoginResponse> login(@Body LoginData loginData);

    @POST("transactions/history")
    Call<HistoryResponse> getHistory(@Query("day") String day);

    @GET("account/validate/{userId}/{amount}")
    Call<String> requestPayment(@Path("userId") String userId, @Path("amount") Double amount);


}
