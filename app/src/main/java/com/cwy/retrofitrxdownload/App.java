package com.cwy.retrofitrxdownload;

import android.app.Application;

import com.cwy.retrofitdownloadlib.http.HttpMethods;

/**
 * Created by Chenwy on 2018/1/6.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpMethods.getInstance().init();
    }
}
