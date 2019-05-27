package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.MessageFormat;

public class SensorGame extends Activity implements OrientationChangeListener, SensorGameResultListener {
    OrientationSensor orientationSensor;
    SensorGameView sensorGameView;
    LinearLayout container;
    TextView count;
    CountDownTimer countDownTimer;
    Handler handler;
    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensorgame);

        count = findViewById(R.id.count);
        container = findViewById(R.id.container);

        handler = new Handler();

        sensorGameView = new SensorGameView(this);

        orientationSensor = new OrientationSensor(this);
        orientationSensor.registerOrientationChangeListener(this);
        sensorGameView.setSensorGameResultListener(this);

        container.addView(sensorGameView);

        countDownTimer = new CountDownTimer(30000, 1000) {
            int secondsUntilFinished;

            @Override
            public void onTick(long millisUntilFinished) {
                secondsUntilFinished = (int) (millisUntilFinished / 1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!gameOver)
                            count.setText(String.valueOf(secondsUntilFinished));
                        else {
                            terminateGame(secondsUntilFinished);
                            cancel();
                        }
                    }
                });
            }

            @Override
            public void onFinish() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        container.removeAllViews();
                        count.setText("병을 지켰습니다!");
                        Intent intent = GameResultActivity.getResultIntent(getApplicationContext(), GameResultActivity.TAG_SENSOR, 100);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        orientationSensor.registerListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        orientationSensor.unregisterListeners();
    }

    @Override
    public void onOrientationChanged(double pitch, double roll) {
        sensorGameView.updateDst(pitch, roll);
    }

    @Override
    public void onGameOver() {
        if (!gameOver) {
            gameOver = true;
            container.removeAllViews();
        }
    }

    public void terminateGame(int secondsUntilFinished) {
        count.setText(MessageFormat.format("병이 깨졌습니다.\n남은 시간 : {0}초", secondsUntilFinished));
        int score = (30 - secondsUntilFinished) * 10 / 3;
        Intent intent = GameResultActivity.getResultIntent(this, GameResultActivity.TAG_SENSOR, score);
        startActivity(intent);
        finish();
    }
}
