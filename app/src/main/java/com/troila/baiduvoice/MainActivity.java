package com.troila.baiduvoice;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.aip.asrwakeup3.core.recog.MyRecognizer;
import com.baidu.aip.asrwakeup3.core.recog.listener.IResultRecogListener;
import com.baidu.aip.asrwakeup3.core.recog.listener.ResultRecogListener;
import com.baidu.aip.asrwakeup3.core.util.FileUtil;
import com.baidu.speech.asr.SpeechConstant;
import com.baidu.aip.asrwakeup3.core.conversion.AndroidAudioConverter;
import com.baidu.aip.asrwakeup3.core.conversion.callback.IConvertCallback;
import com.baidu.aip.asrwakeup3.core.conversion.model.AudioFormat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IResultRecogListener {
    protected MyRecognizer myRecognizer;
    private Button btn;
    private TextView tv;

    IConvertCallback callback = new IConvertCallback() {
        @Override
        public void onSuccess(File convertedFile) {
            // DEMO集成步骤2.1 拼接识别参数： 此处params可以打印出来，直接写到你的代码里去，最终的json一致即可。
//            InFileStream.setInputStream(InFileStream.changeFile(convertedFile.getPath()));

            Map<String, Object> params = new HashMap<>();
            params.put(SpeechConstant.IN_FILE, convertedFile.getPath());
//            map.put(SpeechConstant.IN_FILE,  "#com.baidu.aip.asrwakeup3.core.inputstream.InFileStream.create16kStream()");

            // params 也可以根据文档此处手动修改，参数会以json的格式在界面和logcat日志中打印

            // 复制此段可以自动检测常规错误
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

            // 这里打印出params， 填写至您自己的app中，直接调用下面这行代码即可。
            // DEMO集成步骤2.2 开始识别
            myRecognizer.start(params);
        }
        @Override
        public void onFailure(Exception error) {
            // Oops! Something went wrong
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);
        initSamplePath(this);
        ResultRecogListener listener = new ResultRecogListener();
        listener.setFinalLitener(this);
        myRecognizer = new MyRecognizer(this, listener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(samplePath+"/test1.mp3");
            }
        });
        (findViewById(R.id.btn1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidAudioConverter.with(MainActivity.this)
                        // Your current audio file
                        .setFile(new File(samplePath+"/test1.pcm"))

                        // Your desired audio format
                        .setFormat(AudioFormat.MP3)

                        // An callback to know when conversion is finished
                        .setCallback(new IConvertCallback() {
                            @Override
                            public void onSuccess(File convertedFile) {

                            }

                            @Override
                            public void onFailure(Exception error) {

                            }
                        })

                        // Start conversion
                        .convert();


//                Vibrator vibrator = (Vibrator)getSystemService(MainActivity.this.VIBRATOR_SERVICE);
//                vibrator.vibrate(500);
            }
        });


    }

    @Override
    public void onFinalResult(String result) {
        Log.v("", result + "=======================");
        tv.append(result + "\n");
    }

    @Override
    public void onEnd() {
        Log.v("++++++++++++++++++++","++++++++++++++++++++++++++++结束");
    }

//    private Map saveFile() {
//
//
//        InFileStream.setInputStream(InFileStream.changeFile(samplePath + "/test123.mp3"));
//        Map<String, Object> map = new HashMap<>();
////        map.put(SpeechConstant.IN_FILE, samplePath + "/test123.mp3");
//        map.put(SpeechConstant.IN_FILE,  "#com.baidu.aip.asrwakeup3.core.inputstream.InFileStream.create16kStream()");
//        return map;
//    }


    public void initSamplePath(Context context) {
        String sampleDir = "baiduASR";
        samplePath = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
        if (!FileUtil.makeDir(samplePath)) {
            samplePath = context.getExternalFilesDir(sampleDir).getAbsolutePath();
            if (!FileUtil.makeDir(samplePath)) {
                throw new RuntimeException("创建临时目录失败 :" + samplePath);
            }
        }
    }

    protected String samplePath;

    private void start(String path) {
        AndroidAudioConverter.with(this)
                // Your current audio file
                .setFile(new File(path))

                // Your desired audio format
                .setFormat(AudioFormat.PCM)

                // An callback to know when conversion is finished
                .setCallback(callback)

                // Start conversion
                .convert();
    }

    @Override
    protected void onDestroy() {
        myRecognizer.release();
        super.onDestroy();
    }
}
