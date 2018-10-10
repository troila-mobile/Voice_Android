package com.baidu.aip.asrwakeup3.core.recog.listener;

import android.util.Log;

import com.baidu.aip.asrwakeup3.core.recog.RecogResult;
import com.baidu.speech.asr.SpeechConstant;

public class ResultRecogListener extends StatusRecogListener{
    IResultRecogListener iResultRecogListener;
    @Override
    public void onAsrFinalResult(String[] results, RecogResult recogResult) {
        iResultRecogListener.onFinalResult(results[0]);
    }
    @Override
    public void onAsrExit() {
        iResultRecogListener.onEnd();
    }

    public void setFinalLitener(IResultRecogListener iResultRecogListener) {
        this.iResultRecogListener = iResultRecogListener;
    }
}
