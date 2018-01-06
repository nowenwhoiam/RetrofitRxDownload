package com.cwy.retrofitdownloadlib.http;

import android.content.Context;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chenwy on 2017/10/16.
 */

public class HttpMethods {
    private Retrofit retrofit;
    private static final int TIME_OUT = 15;
    private DownloadService downloadService;


    public HttpMethods() {
    }

    private static class HttpMethodsHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return HttpMethodsHolder.INSTANCE;
    }

    public void init(){
        init(null,"http://www.baseurl.com");
    }

    public void init(Context context,String baseUrl) {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new DownloadInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        downloadService = retrofit.create(DownloadService.class);
    }

    public DownloadService getDownloadService() {
        return downloadService;
    }
}
