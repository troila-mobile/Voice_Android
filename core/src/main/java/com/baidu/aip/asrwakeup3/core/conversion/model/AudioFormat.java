package com.baidu.aip.asrwakeup3.core.conversion.model;

public enum AudioFormat {
    AAC,
    MP3,
    M4A,
    WMA,
    WAV,
    PCM,
    FLAC;

    public String getFormat() {
        return name().toLowerCase();
    }
}