package com.example.a82102.oneshot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.impl.util.PermissionUtils;

import java.util.Arrays;

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

    String[] splitString(String string) { //문장을 String string 매개변수로 받음
        String[] strings = string.split("\\s");  //받은 문장을 띄어쓰기 기준으로 나눔
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(strings[i]); //나눠진 문장을 다시 합쳐서 띄어쓰기 없는 문장으로 만들어줌
        }
        string = stringBuilder.toString();
        strings = string.split(""); //띄어쓰기가 없어진 문장을 한 글자씩 나눠줌
        String[] strings2 = new String[strings.length - 1];
        for (int i = 0; i < strings2.length; i++) {
            strings2[i] = strings[i + 1]; //글자 하나하나 배열에 넣어줌
        }
        Log.i("김치전", Arrays.toString(strings2));
        return strings2; //번지당 글자 하나씩 담긴 배열을 반환
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_game);

        textView = findViewById(R.id.sample);
        scoreText = findViewById(R.id.score);

        sample = "술은 인간의 성품을 비추는 거울이다"; //샘플 문장, 마음대로 바꿀 수 있음
        textView.setText(sample);

        sampleArray = splitString(sample);

        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);

        setButtonsStatus(true);
    }

    public void setButtonsStatus(boolean enabled) { //버튼의 활성/비활성 상태를 변경해주는 메소드
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
                    client = new SpeechRecognizerClient.Builder().
                            setServiceType(SpeechRecognizerClient.SERVICE_TYPE_DICTATION).build();

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
        result = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS).get(0);
        editText = findViewById(R.id.text);
        editText.setText(result);

        resultArray = splitString(result);

        count = 0;

        for (int i = 0; i < sampleArray.length; i++) { //배열 원소를 비교해주는 과정
            for (int j = 0; j < resultArray.length; j++) {
                if (sampleArray[i].equals(resultArray[j])) {
                    count++;
                    break;
                }
            }
        }
        Log.i("김치전", String.valueOf(count));

        score = (double) count / sampleArray.length * 100; //정확도를 계산하는 과정
        scoreText.setText(String.valueOf(score));

        setButtonsStatus(true);
        client = null;
    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {

    }
}