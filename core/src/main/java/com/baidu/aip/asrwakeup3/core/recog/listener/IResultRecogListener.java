package com.baidu.aip.asrwakeup3.core.recog.listener;

import com.baidu.aip.asrwakeup3.core.recog.RecogResult;

public interface IResultRecogListener {
    void onFinalResult(String result);
    void onEnd();

}
