package com.troila.baiduvoice;

import android.app.Application;
import android.widget.Toast;

import com.baidu.aip.asrwakeup3.core.conversion.AndroidAudioConverter;
import com.baidu.aip.asrwakeup3.core.conversion.callback.ILoadCallback;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
                Toast.makeText(App.this,"load",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
            }
        });
    }
}
