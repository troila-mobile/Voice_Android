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
  compile ''
}
```

