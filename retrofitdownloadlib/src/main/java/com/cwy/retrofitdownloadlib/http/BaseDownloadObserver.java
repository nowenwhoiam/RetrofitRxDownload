package com.cwy.retrofitdownloadlib.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/1/5 16:28
 */

public abstract class BaseDownloadObserver<T> implements Observer<T> {
    @Override
    public abstract void onSubscribe(Disposable d);

    @Override
    public void onNext(T t) {
        onDownloadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownloadError(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onDownloadSuccess(T t);

    protected abstract void onDownloadError(Throwable e);
}
