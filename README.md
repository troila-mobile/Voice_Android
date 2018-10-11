## How To Use

1 - Add this permission into your `AndroidManifest.xml` and [request in Android 6.0+](https://developer.android.com/training/permissions/requesting.html)
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

2 - Load the lib inside your `Application` class
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
            }
        });
    }
}
```

3 - Convert audio files async
```java
File flacFile = new File(Environment.getExternalStorageDirectory(), "my_audio.flac");
IConvertCallback callback = new IConvertCallback() {
    @Override
    public void onSuccess(File convertedFile) {
        // So fast? Love it!
    }
    @Override
    public void onFailure(Exception error) {
        // Oops! Something went wrong
    }
};
AndroidAudioConverter.with(this)
    // Your current audio file
    .setFile(flacFile)  
    
    // Your desired audio format 
    .setFormat(AudioFormat.MP3)
    
    // An callback to know when conversion is finished
    .setCallback(callback)
    
    // Start conversion
    .convert();
```

## Import to your project
Put this into your `app/build.gradle`:
```
repositories {
  maven {
    url "https://jitpack.io"
  }
}

dependencies {
	       implementation 'com.github.troila-mobile:Voice_Android:master-SNAPSHOT'
	}
```
## 语音转文字
1 - init
```

ResultRecogListener listener = new ResultRecogListener();
        listener.setFinalLitener(new IResultRecogListener() {
            @Override
            public void onFinalResult(String result) {
		//最终结果
            }

            @Override
            public void onEnd() {
	    //识别结束

            }
        });
     MyRecognizer   myRecognizer = new MyRecognizer(this, listener);
```
2 conversion
```
   Map<String, Object> params = new HashMap<>();
            params.put(SpeechConstant.IN_FILE, convertedFile.getPath());
            (new AutoCheck(getApplicationContext(), new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 100) {
                        AutoCheck autoCheck = (AutoCheck) msg.obj;
                        synchronized (autoCheck) {
                            String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();

//                        ; // 可以用下面一行替代，在logcat中查看代码
                            Log.w("AutoCheckMessage", message + "\n");
                        }
                    }
                }
            }, false)).checkAsr(params);

            myRecognizer.start(params);
```


