package com.cwy.retrofitdownloadlib.http;

import java.io.File;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Chenwy on 2018/1/5 16:57
 */

public class DownloadHelper {
    private FileDownloadCallback<File> fileDownloadCallback;
    private Disposable disposable;

    private static class DownloadHelperHolder {
        private static final DownloadHelper INSTANCE = new DownloadHelper();
    }

    public static DownloadHelper getInstance() {
        return DownloadHelperHolder.INSTANCE;
    }

    public void downloadFile(String url, final String destDir, final String fileName, final FileDownloadCallback<File> fileDownLoadObserver) {
        release();
        this.fileDownloadCallback = fileDownLoadObserver;
        fileDownLoadObserver.registerProgressEventBus();
        HttpMethods.getInstance()
                .getDownloadService()
                .download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .observeOn(Schedulers.computation())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        return fileDownLoadObserver.saveFile(responseBody.byteStream(), destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseDownloadObserver<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    protected void onDownloadSuccess(File file) {
                        fileDownLoadObserver.onDownLoadSuccess(file);
                    }

                    @Override
                    protected void onDownloadError(Throwable e) {
                        fileDownLoadObserver.onDownLoadFail(e);
                    }
                });
    }

    public void release() {
        if (fileDownloadCallback != null) {
            fileDownloadCallback.unRegisterProgressEventBus();
            fileDownloadCallback = null;
        }

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
