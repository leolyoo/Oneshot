package com.example.a82102.oneshot;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Random;

public class MemoryGame extends Activity implements View.OnClickListener {
    TextView textView;

    Random random;
    int[] randomNums;
    int stage = 0;
    int clickedIndex;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorygame);
        textView = findViewById(R.id.textView);
        setButtonsListenable(this);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText(MessageFormat.format("{0}단계 : ", stage));
                clickedIndex = 0;
                setButtonsStatus(true);
            }
        };
        random = new Random();
        setButtonsStatus(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                findViewById(R.id.start).setEnabled(false);
                setStage();
                break;
            case R.id.button1:
                checkNumber(1);
                break;
            case R.id.button2:
                checkNumber(2);
                break;
            case R.id.button3:
                checkNumber(3);
                break;
            case R.id.button4:
                checkNumber(4);
                break;
            case R.id.button5:
                checkNumber(5);
                break;
            case R.id.button6:
                checkNumber(6);
                break;
            case R.id.button7:
                checkNumber(7);
                break;
            case R.id.button8:
                checkNumber(8);
                break;
            case R.id.button9:
                checkNumber(9);
                break;
        }
    }

    private void setButtonsListenable(View.OnClickListener onClickListener) {
        findViewById(R.id.start).setOnClickListener(onClickListener);
        findViewById(R.id.button1).setOnClickListener(onClickListener);
        findViewById(R.id.button2).setOnClickListener(onClickListener);
        findViewById(R.id.button3).setOnClickListener(onClickListener);
        findViewById(R.id.button4).setOnClickListener(onClickListener);
        findViewById(R.id.button5).setOnClickListener(onClickListener);
        findViewById(R.id.button6).setOnClickListener(onClickListener);
        findViewById(R.id.button7).setOnClickListener(onClickListener);
        findViewById(R.id.button8).setOnClickListener(onClickListener);
        findViewById(R.id.button9).setOnClickListener(onClickListener);
    }

    void setStage() { //단계를 올려주고 화면에 표시한 후 3초 기다리게 해주는 스레드를 만들어 주는 메소드
        stage++;
        setButtonsStatus(false);
        randomNums = createRandomNums(stage);
        textView.setText(MessageFormat.format("{0}단계 : {1}", stage, Arrays.toString(randomNums)));
        handler.postDelayed(runnable, 3000);
    }

    void checkNumber(int clickedNum) { //숫자를 체크하고 틀리면 결과를 알려주는 메서드

        if (randomNums[clickedIndex] == clickedNum) {
            textView.setText(MessageFormat.format("{0}단계 : {1}번째 자리 정답!", stage, clickedIndex + 1));
            if (clickedIndex == randomNums.length - 1) {
                setStage();
            }
            clickedIndex++;
        } else {
            textView.setText(MessageFormat.format("{0}단계 : 틀렸습니다.", stage));
            stage = 0;
            setButtonsStatus(false);
            findViewById(R.id.start).setEnabled(true);
        }
    }

    void setButtonsStatus(boolean enabled) { //버튼 활성/비활성 상태를 변경해주는 메소드
        findViewById(R.id.button1).setEnabled(enabled);
        findViewById(R.id.button2).setEnabled(enabled);
        findViewById(R.id.button3).setEnabled(enabled);
        findViewById(R.id.button4).setEnabled(enabled);
        findViewById(R.id.button5).setEnabled(enabled);
        findViewById(R.id.button6).setEnabled(enabled);
        findViewById(R.id.button7).setEnabled(enabled);
        findViewById(R.id.button8).setEnabled(enabled);
        findViewById(R.id.button9).setEnabled(enabled);
    }

    int[] createRandomNums(int count) { //단계를 int count로 받아 랜덤한 숫자 배열을 만들어 반환해주는 메소드
        randomNums = new int[count];
        for (int i = 0; i < randomNums.length; i++) {
            randomNums[i] = random.nextInt(8) + 1;
        }
        return randomNums;
    }
}
