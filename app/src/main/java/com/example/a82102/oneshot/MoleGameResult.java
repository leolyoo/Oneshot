package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoleGameResult extends Activity {

    TextView sub_result;
    Button sub_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_molegame_result); //주소로 알고있는 xml을 눈에 보이는 view로 바꿔줌 ->InfLate

        sub_result = findViewById(R.id.sub_result);
        sub_retry = findViewById(R.id.sub_retry);

        int score = getIntent().getIntExtra("score", -1);
        sub_result.setText(String.valueOf(score));

        sub_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoleGameResult.this, MoleGame.class);
                startActivity(intent);
                finish();
            }
        });

    }
}