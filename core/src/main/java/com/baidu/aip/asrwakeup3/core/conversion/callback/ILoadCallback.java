package com.baidu.aip.asrwakeup3.core.conversion.callback;

public interface ILoadCallback {
    
    void onSuccess();
    
    void onFailure(Exception error);
    
}