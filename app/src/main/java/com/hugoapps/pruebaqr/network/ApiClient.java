package com.hugoapps.pruebaqr.network;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static String APP_ID = "token";
    private static String APP_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZFVzdWFyaW8iOiI2Yzc4YWYxYS0yZTdlLTQ5ZDMtYjAxNS1lZDI3YTlmNDgzNWQiLCJub21icmVVc3VhcmlvIjoidGVzdHdlYiIsImlhdCI6MTcwNTA1NzAyMSwiaXNzIjoiaHR0cHM6Ly93d3cuYWR6Z2kuY29tIiwianRpIjoiIn0.J9SasbEaxwU2hlG5YRpDEeEJc8vZgb6cVYzj3cRNo84";
    public static Retrofit getClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //Create a new Interceptor.
        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("app_id",APP_ID)
                        .addHeader("app_key", APP_KEY)
                        .build();
                return chain.proceed(request);
            }
        };

        clientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC).setLevel
                (HttpLoggingInterceptor.Level.BODY).setLevel(HttpLoggingInterceptor.Level.HEADERS));
        //Add the interceptor to the client builder.
        clientBuilder.addInterceptor(headerAuthorizationInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://82.98.132.218:6587/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getClient2(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //Create a new Interceptor.
        Interceptor headerAuthorizationInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("app_id",APP_ID)
                        .addHeader("app_key", APP_KEY)
                        .build();
                return chain.proceed(request);
            }
        };

        clientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC).setLevel
                (HttpLoggingInterceptor.Level.BODY).setLevel(HttpLoggingInterceptor.Level.HEADERS));
        //Add the interceptor to the client builder.
        clientBuilder.addInterceptor(headerAuthorizationInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://datos.adzgi.com:6587/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
