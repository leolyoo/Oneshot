package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.impl.util.PermissionUtils;

import java.util.ArrayList;
import java.util.Random;

public class VoiceGame extends Activity implements View.OnClickListener, SpeechRecognizeListener {
    EditText editText;
    TextView textView, scoreText;
    SpeechRecognizerClient client;

    String sample;
    String result;
    String[] sampleArray;
    String[] resultArray;

    int count;
    double score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_game);

        textView = findViewById(R.id.sample);
        scoreText = findViewById(R.id.score);
        editText = findViewById(R.id.text);

        String[] samples = {"술이 머리에 들어가면 비밀이 밖으로 밀려나간다", "술은 인간의 성품을 비추는 거울이다", "술은 행복한 자에게만 달콤하다", "술을 물처럼 마시는 자는 술을 마실 가치가 없다"};
        sample = samples[new Random().nextInt(samples.length)];
        textView.setText(sample);

        sampleArray = sample.replaceAll("\\s", "").split("");

        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);

        setButtonsStatus(true);
    }

    public void setButtonsStatus(boolean enabled) { //버튼의 활성/비활성 상태를 변경해주는 메소드
        if (enabled) {
            findViewById(R.id.start).setVisibility(View.VISIBLE);
            findViewById(R.id.stop).setVisibility(View.GONE);
            findViewById(R.id.cancel).setVisibility(View.GONE);
        } else {
            findViewById(R.id.start).setVisibility(View.GONE);
            findViewById(R.id.stop).setVisibility(View.VISIBLE);
            findViewById(R.id.cancel).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.start).setEnabled(enabled);
        findViewById(R.id.stop).setEnabled(!enabled);
        findViewById(R.id.cancel).setEnabled(!enabled);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechRecognizerManager.getInstance().finalizeLibrary(); //화면이 꺼지거나 앱에서 나갈 경우 음성인식 라이브러리 해제
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (PermissionUtils.checkAudioRecordPermission(this)) {
                    client = new SpeechRecognizerClient.Builder().setServiceType(SpeechRecognizerClient.SERVICE_TYPE_DICTATION).build();
                    client.setSpeechRecognizeListener(this);
                    client.startRecording(true);
                    setButtonsStatus(false);
                }
                break;
            case R.id.stop:
                if (client != null) {
                    client.stopRecording();
                }
                break;
            case R.id.cancel:
                if (client != null) {
                    client.cancelRecording();
                }
                setButtonsStatus(true);
                break;
        }

    }

    @Override
    public void onReady() {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {

    }

    @Override
    public void onPartialResult(String partialResult) {

    }

    @Override
    public void onResults(Bundle results) { //SpeechRecognizeListener를 상속받음으로써 오버라이드된 메소드, 음성인식 결과를 Bundle results에 담아줌
        ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        if (!texts.isEmpty()) {
            result = texts.get(0);
            editText.setText(result);
        }

        resultArray = result.replaceAll("\\s", "").split("");

        count = 0;

        for (String aSampleArray : sampleArray) { //배열 원소를 비교해주는 과정
            for (String aResultArray : resultArray) {
                if (aSampleArray.equals(aResultArray)) {
                    count++;
                    break;
                }
            }
        }

        score = (double) count / sampleArray.length * 100; //정확도를 계산하는 과정
        scoreText.setText(String.valueOf(score));

        setButtonsStatus(true);
        client = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                Intent intent = GameResultActivity.getResultIntent(getApplicationContext(), GameResultActivity.TAG_VOICE, (int) score);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {

    }
}