package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Game extends Activity implements View.OnClickListener{

    ImageButton ml, mm, vi, ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ml = (ImageButton) findViewById(R.id.mole);
        mm = (ImageButton) findViewById(R.id.memory);
        vi = (ImageButton) findViewById(R.id.voice);
        ss = (ImageButton) findViewById(R.id.sensor);

        findViewById(R.id.mole).setOnClickListener(this);
        findViewById(R.id.memory).setOnClickListener(this);
        findViewById(R.id.voice).setOnClickListener(this);
        findViewById(R.id.sensor).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mole :
                startActivity(new Intent(this, MoleGame.class));
                break;
            case R.id.memory :
                startActivity(new Intent(this, MemoryGame.class));
                break;
            case R.id.voice :
                startActivity(new Intent(this, VoiceGame.class));
                break;
            case R.id.sensor :
                startActivity(new Intent(this, SensorGame.class));
                break;
        }
    }
}
