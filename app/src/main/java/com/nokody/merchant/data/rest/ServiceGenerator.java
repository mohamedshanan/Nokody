package com.nokody.merchant.data.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static final String BASE_URL = "http://23.99.136.230/";
    private static Retrofit.Builder localPlacesBuilder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static WebServices mPlacesPointInterface;

    public static <S> S createLocalPlacesService(Class<S> serviceClass) {

        httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        Retrofit retrofit = localPlacesBuilder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static WebServices getEndPointInterface() {
        if (mPlacesPointInterface == null) {
            mPlacesPointInterface = ServiceGenerator.createLocalPlacesService(WebServices.class);
        }
        return mPlacesPointInterface;
    }
}
