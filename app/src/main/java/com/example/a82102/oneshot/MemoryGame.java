package com.example.a82102.oneshot;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MemoryGame extends Activity implements View.OnClickListener {
    TextView textView;

    Random random;
    int[] randomNums;
    int count = 0;
    int clickedIndex;
    MyHandler myHandler;

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                textView.setText(count + "단계 : ");
                clickedIndex = 0;
                setButtonsStatus(true);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorygame);

        textView = findViewById(R.id.textView);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        myHandler = new MyHandler();
        random = new Random();
        setButtonsStatus(false);
    }

    public void playMemoryGame() { //단계를 올려주고 화면에 표시한 후 3초 기다리게 해주는 스레드를 만들어 주는 메소드
        count++;
        setButtonsStatus(false);
        randomNums = createRandomNums(count);
        textView.setText(count + "단계 : " + Arrays.toString(randomNums));
        new MyThread().start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                playMemoryGame();
                findViewById(R.id.start).setEnabled(false);
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

    void checkNumber(int clickedNum) { //숫자를 체크하고 틀리면 결과를 알려주는 메서드

        if (randomNums[clickedIndex] == clickedNum) {
            textView.setText(count + "단계 : " + (clickedIndex + 1) + "번째 자리 정답!");
            if (clickedIndex == randomNums.length - 1) {
                playMemoryGame();
            }
            clickedIndex++;
        } else {
            textView.setText(count + "단계 : 틀렸습니다.");
            count = 0;
            setButtonsStatus(false);
            findViewById(R.id.start).setEnabled(true);
        }
    }

    public void setButtonsStatus(boolean enabled) { //버튼 활성/비활성 상태를 변경해주는 메소드
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

    public int[] createRandomNums(int count) { //단계를 int count로 받아 랜덤한 숫자 배열을 만들어 반환해주는 메소드
        randomNums = new int[count];
        for (int i = 0; i < randomNums.length; i++) {
            randomNums[i] = random.nextInt(8) + 1;
        }
        return randomNums;
    }

    class MyThread extends Thread { //3초 기다리는 스레드
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            myHandler.sendEmptyMessage(1);
        }
    }
}
