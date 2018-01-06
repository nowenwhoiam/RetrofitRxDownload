package com.cwy.retrofitdownloadlib.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Chenwy on 2018/1/5 10:26
 */

public interface DownloadService {
    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载
}
