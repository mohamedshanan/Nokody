package com.nokody.merchant.data.repositories;

import com.nokody.merchant.data.models.LoginData;
import com.nokody.merchant.data.models.LoginResponse;
import com.nokody.merchant.data.models.callbacks.LoginCallBack;
import com.nokody.merchant.data.rest.ServiceGenerator;
import com.nokody.merchant.data.rest.WebServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepo {

    private static final String TAG = AuthRepo.class.getSimpleName();
    private WebServices apiEndPointInterface;

    private static AuthRepo instance;

    private AuthRepo() {
        apiEndPointInterface = ServiceGenerator.getEndPointInterface();
    }

    public static AuthRepo getInstance() {
        if (instance == null) {
            instance = new AuthRepo();
        }
        return instance;
    }

    public void login(LoginData loginData, LoginCallBack loginCallBack) {

        apiEndPointInterface.login(loginData)
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response != null && response.isSuccessful()){
                            loginCallBack.onSuccess(response.body());
                        } else {
                            loginCallBack.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        loginCallBack.onFailure();
                    }
                });
    }

}
