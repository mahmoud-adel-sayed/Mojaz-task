package com.mojaz.assignment.api;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit = null;

    // Our http client
    private static final OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool(15, 500000, TimeUnit.MILLISECONDS))
            .readTimeout(60, TimeUnit.SECONDS);

    private ApiClient() {}

    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
