package com.gordon.main.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Gordon
 * @since 2017/4/12
 * do()
 */
public class APIClient {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    private APIClient() {
    }

    public APIClient(String baseUrl, Converter.Factory factory, Interceptor interceptor) {
        // 初始化OkHttpClient
        mOkHttpClient = new OkHttpClient.Builder()
                // 忽略SSL验证
                .hostnameVerifier(getHostnameVerifier())
                .addInterceptor(interceptor)
                .build();

        // 初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public Retrofit retrofit() {
        return mRetrofit;
    }

    protected HostnameVerifier getHostnameVerifier() {
        return new AllowAllHostnameVerifier();
    }

}