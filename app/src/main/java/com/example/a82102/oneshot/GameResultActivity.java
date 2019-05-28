package com.example.a82102.oneshot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GameResultActivity extends Activity {
    public static final String TAG_MOLE = "mole";
    public static final String TAG_MEMORY = "memory";
    public static final String TAG_SENSOR = "sensor";
    public static final String TAG_VOICE = "voice";
    Intent intent;
    String tag = "";
    int score = 0;
    TextView tagTextView;
    TextView scoreTextView;
    ImageView resultImageView;

    public static Intent getResultIntent(Context context, String tag, int score) {
        Intent intent = new Intent(context, GameResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        bundle.putInt("score", score);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            tag = bundle.getString("tag");
            score = bundle.getInt("score", 0);
        }

        tagTextView = findViewById(R.id.tag);
        scoreTextView = findViewById(R.id.score);
        resultImageView = findViewById(R.id.image_result);


        tagTextView.setText(tag);
        scoreTextView.setText(String.valueOf(score));

        if (tag.equalsIgnoreCase(TAG_MOLE)) {
            if (score >= 90) {
                resultImageView.setImageResource(R.drawable.mole_sober);
            } else if (score >= 60) {
                resultImageView.setImageResource(R.drawable.mole_tipsy);
            } else {
                resultImageView.setImageResource(R.drawable.mole_drunk);
            }
        } else if (tag.equalsIgnoreCase(TAG_MEMORY)) {
            if (score >= 90) {
                resultImageView.setImageResource(R.drawable.memory_sober);
            } else if (score >= 60) {
                resultImageView.setImageResource(R.drawable.memory_tipsy);
            } else {
                resultImageView.setImageResource(R.drawable.memory_drunk);
            }
        } else if (tag.equalsIgnoreCase(TAG_VOICE)) {
            if (score >= 90) {
                resultImageView.setImageResource(R.drawable.voice_sober);
            } else if (score >= 60) {
                resultImageView.setImageResource(R.drawable.voice_tipsy);
            } else {
                resultImageView.setImageResource(R.drawable.voice_drunk);
            }
        } else if (tag.equalsIgnoreCase(TAG_SENSOR)) {
            if (score >= 90) {
                resultImageView.setImageResource(R.drawable.sensor_sober);
            } else if (score >= 60) {
                resultImageView.setImageResource(R.drawable.sensor_tipsy);
            } else {
                resultImageView.setImageResource(R.drawable.sensor_drunk);
            }
        }
    }
}
