package com.cwy.retrofitdownloadlib.http;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chenwy on 2018/1/5 10:51
 */

public abstract class FileDownloadCallback<T> {
    //可以重写，具体可由子类实现
    public void onComplete() {
    }

    //下载成功的回调
    public abstract void onDownLoadSuccess(T t);

    //下载失败回调
    public abstract void onDownLoadFail(Throwable e);

    //下载进度监听
    public abstract void onProgress(int progress, int total);

    /**
     * 将文件写入本地
     *
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(InputStream is, String destFileDir, String destFileName) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return file;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerProgressEventBus() {
        EventBus.getDefault().register(this);
    }

    public void unRegisterProgressEventBus() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void progress(DownloadProgressEvent downloadProgressEvent) {
        onProgress(downloadProgressEvent.progress, downloadProgressEvent.total);
    }

}
