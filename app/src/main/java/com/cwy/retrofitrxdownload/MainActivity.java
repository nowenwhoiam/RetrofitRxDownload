package com.cwy.retrofitrxdownload;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cwy.retrofitdownloadlib.http.DownloadHelper;
import com.cwy.retrofitdownloadlib.http.FileDownloadCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_test);
        url = "http://imtt.dd.qq.com/16891/F3FB1B8E292D1CB24151D3FEF6F348D7.apk" +
                "?fsname=com.tencent.mobileqq_7.3.2_762.apk&csr=2097&_track_d99957f7" +
                "=947eb6a0-df23-4a5d-b708-ae1a9e17e224";
    }

    public void download(View view) {
        DownloadHelper.getInstance()
                .downloadFile(url, Environment.getExternalStorageDirectory() + File.separator + "/apk", "QQ.apk",
                        new FileDownloadCallback<File>() {
                            @Override
                            public void onDownLoadSuccess(File file) {
                                tv.setText("下载成功。\n" + file.getAbsolutePath());
                            }

                            @Override
                            public void onDownLoadFail(Throwable e) {
                                tv.setText("下载失败 : " + e.getLocalizedMessage());
                            }

                            @Override
                            public void onProgress(int progress, int total) {
                                tv.setText("下载中 : " + progress + "%");
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        DownloadHelper.getInstance().release();
        super.onDestroy();
    }
}
